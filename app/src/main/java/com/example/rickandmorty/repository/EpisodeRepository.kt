package com.example.rickandmorty.repository

import com.example.rickandmorty.api.EpisodeApi
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.episodes.EpisodesDao
import com.example.rickandmorty.data.network.EpisodeResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val episodesDao: EpisodesDao,
    private val episodeApi: EpisodeApi
) {

    fun getEpisodes(character: CharactersResults): Observable<List<EpisodeResponse>> {
        return getEpisodesFromDb(character)
    }

//    fun fetchCharacterEpisodes(episodeUrl: String): Single<EpisodeResponse> {
//        return episodeApi.getCharacterEpisodes(episodeUrl)
//    }

    fun storeEpisodesInDb(episodes: EpisodeResponse) {
        episodesDao.insertEpisodes(episodes)
    }

    private fun fetchAllEpisodes(character: CharactersResults): Observable<EpisodeResponse> {
        return Observable.range(0, character.episode.size)
            .flatMap { episodeNumber ->
                episodeApi.getCharacterEpisodes(character.episode[episodeNumber])
                    .subscribeOn(Schedulers.io())
            }.doOnNext {
                val episode =  EpisodeResponse(
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

    private fun getEpisodesFromApi(character: CharactersResults): Observable<EpisodeResponse> {
        return fetchAllEpisodes(character)
    }

    private fun getEpisodesFromDb(character: CharactersResults): Observable<List<EpisodeResponse>> {
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

//    fun getEpisodesFromDb(characterId: String): List<EpisodeResponse> {
//        return episodesDao.getEpisodes(characterId)
//    }

}