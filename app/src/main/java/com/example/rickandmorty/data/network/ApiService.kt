package com.example.rickandmorty.data.network

import com.example.rickandmorty.api.CharactersApi
import com.example.rickandmorty.api.EpisodeApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://rickandmortyapi.com/api/"

interface ApiService {

    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideRetrofit(Url: String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideCharactersApi(retrofit: Retrofit): CharactersApi {
        return retrofit.create(CharactersApi::class.java)
    }

    fun charactersApi(): CharactersApi {
        return provideCharactersApi(provideRetrofit())
    }

    fun loadMoreCharactersApi(charactersUrl: String): CharactersApi {
        return provideCharactersApi(provideRetrofit(charactersUrl))
    }

    fun loadCharacterEpisode(episodeUrl: String): EpisodeApi {
        return provideEpisodesApi(provideRetrofit(episodeUrl))
    }

    fun provideEpisodesApi(retrofit: Retrofit): EpisodeApi {
        return retrofit.create(EpisodeApi::class.java)
    }

}