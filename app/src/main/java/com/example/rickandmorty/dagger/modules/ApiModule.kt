package com.example.rickandmorty.dagger.modules

import com.example.rickandmorty.api.CharactersApi
import com.example.rickandmorty.api.EpisodeApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://rickandmortyapi.com/api/"

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCharactersApi(retrofit: Retrofit): CharactersApi {
        return retrofit.create(CharactersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEpisodesApi(retrofit: Retrofit): EpisodeApi {
        return retrofit.create(EpisodeApi::class.java)
    }
}