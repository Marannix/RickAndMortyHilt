package com.example.rickandmorty.repository

import com.example.rickandmorty.api.EpisodeApi
import com.example.rickandmorty.data.episodes.EpisodesDao
import com.example.rickandmorty.data.network.EpisodeResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val episodesDao: EpisodesDao,
    private val episodeApi: EpisodeApi
) {

    fun getEpisodes(characterId: String, episodesUrl: List<String>): Observable<List<EpisodeResponse>> {
        return getEpisodesFromDb(characterId, episodesUrl)
    }

//    fun fetchCharacterEpisodes(episodeUrl: String): Single<EpisodeResponse> {
//        return episodeApi.getCharacterEpisodes(episodeUrl)
//    }

    fun storeEpisodesInDb(episodes: EpisodeResponse) {
        episodesDao.insertEpisodes(episodes)
    }

    private fun fetchAllEpisodes(episodeUrl: List<String>): Observable<EpisodeResponse> {
        return Observable.range(0, episodeUrl.size)
            .flatMap { episodeNumber ->
                episodeApi.getCharacterEpisodes(episodeUrl[episodeNumber])
                    .subscribeOn(Schedulers.io())
            }.doOnNext {
                episodesDao.insertEpisodes(it)
            }
    }

    private fun getEpisodesFromApi(episodesUrl: List<String>): Observable<EpisodeResponse> {
        return fetchAllEpisodes(episodesUrl)
    }

    private fun getEpisodesFromDb(characterId: String, episodesUrl: List<String>): Observable<List<EpisodeResponse>> {
        return episodesDao.getEpisodes(characterId)
            .toObservable()
            .flatMap { listOfEpisodes ->
                if (listOfEpisodes.isEmpty()) {
                    getEpisodesFromApi(episodesUrl).toList().toObservable()
                } else {
                    Observable.just(listOfEpisodes)
                }
            }
    }

//    fun getEpisodesFromDb(characterId: String): List<EpisodeResponse> {
//        return episodesDao.getEpisodes(characterId)
//    }

}