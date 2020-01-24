package com.example.rickandmorty.repository

import com.example.rickandmorty.api.EpisodeApi
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.episodes.EpisodesDao
import com.example.rickandmorty.data.characters.CharacterEpisodeResponse
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

//    fun getAllEpisodes(): Observable<List<CharacterEpisodeResponse>> {
//        return getAllEpisodesFromDb()
//    }

    private fun fetchAllCharacterEpisodes(character: CharactersResults): Observable<CharacterEpisodeResponse> {
        return Observable.range(0, character.episode.size)
            .flatMap { episodeNumber ->
                episodeApi.getCharacterEpisodes(character.episode[episodeNumber])
                    .subscribeOn(Schedulers.io())
            }.doOnNext {
                val episode = CharacterEpisodeResponse(
                    it.episodeId,
                    character.id,
                    it.id,
                    it.name,
                    it.airdate,
                    it.episode
                )
                episodesDao.insertEpisodes(episode)
            }
    }

//    private fun fetchAllEpisodes(nextUrl: String): Observable<CharacterEpisodeResponse> {
//        return episodeApi.getAllEpisodes(nextUrl)
//            .concatMap {
//                if (it.)
//            }
//    }

    private fun getEpisodesFromApi(character: CharactersResults): Observable<CharacterEpisodeResponse> {
        return fetchAllCharacterEpisodes(character)
    }

//    private fun getAllEpisodesFromApi(character: CharactersResults): Observable<CharacterEpisodeResponse> {
//        return fetchAllEpisodes(character)
//    }

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

//    private fun getAllEpisodesFromDb() : Observable<List<CharacterEpisodeResponse>> {
//        return episodesDao.getAllEpisodes()
//            .toObservable()
//            .flatMap { listOfAllEpisodes ->
//                if(listOfAllEpisodes.isEmpty()) {
//                    getAl
//                }
//            }
//    }

}