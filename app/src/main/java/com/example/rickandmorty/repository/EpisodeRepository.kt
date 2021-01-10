package com.example.rickandmorty.repository

import com.example.rickandmorty.api.EpisodeApi
import com.example.rickandmorty.data.characters.CharacterEpisodeResponse
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.episodes.EpisodesDao
import com.example.rickandmorty.data.episodes.EpisodesResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val episodesDao: EpisodesDao,
    private val episodeApi: EpisodeApi
) {

    fun getEpisodes(character: CharactersResults): Observable<List<CharacterEpisodeResponse>> {
        return getEpisodesFromDb(character)
    }

    fun getAllEpisodes(): Observable<List<EpisodesResult>> {
        return getAllEpisodesFromDb()
    }

    private fun getEpisodesFromDb(character: CharactersResults): Observable<List<CharacterEpisodeResponse>> {
        return episodesDao.getEpisodes(character.id)
            .toObservable()
            .flatMap { listOfEpisodes ->
                if (listOfEpisodes.isEmpty()) {
                    getEpisodesFromApi(character).toList().toObservable()
                } else {
                    Observable.just(listOfEpisodes)
                }
            }
    }

    private fun getAllEpisodesFromDb(): Observable<List<EpisodesResult>> {
        return episodesDao.getAllEpisodes()
            .toObservable()
            .flatMap { listOfAllEpisodes ->
                if (listOfAllEpisodes.isEmpty()) {
                    getAllEpisodesFromApi().toList().toObservable()
                } else {
                    Observable.just(listOfAllEpisodes)
                }
            }
    }


    private fun getEpisodesFromApi(character: CharactersResults): Observable<CharacterEpisodeResponse> {
        return fetchAllCharacterEpisodes(character)
    }

    private fun getAllEpisodesFromApi(): Observable<EpisodesResult> {
        //todo need a way to remove this url from here
        return fetchNextEpisodes("https://rickandmortyapi.com/api/episode/?page=1")
            .concatMap { listOfEpisodes ->
                Observable.fromIterable(listOfEpisodes)
            }
    }

    private fun fetchAllCharacterEpisodes(character: CharactersResults): Observable<CharacterEpisodeResponse> {
        return Observable.range(0, character.episode.size)
            .flatMap { episodeNumber ->
                episodeApi.getCharacterEpisodes(character.episode[episodeNumber])
                    .subscribeOn(Schedulers.io())
            }.doOnNext {
                val episode = CharacterEpisodeResponse(
                    it.id,
                    character.id,
                    it.name,
                    it.airdate,
                    it.episode
                )
                episodesDao.insertCharacterEpisodes(episode)
            }
    }

    private fun fetchNextEpisodes(nextUrl: String): Observable<List<EpisodesResult>> {
        return episodeApi.getNextEpisodes(nextUrl)
            .subscribeOn(Schedulers.io())
            .concatMap { response ->
                if (response.episodesPageInfo.next.isNullOrEmpty()) {
                    Observable.just(response.episodes)
                } else {
                    Observable.just(response.episodes)
                        .concatWith(fetchNextEpisodes(response.episodesPageInfo.next))
                }
            }
            .doOnNext {
                episodesDao.insertAllEpisodes(it)
            }
    }

}