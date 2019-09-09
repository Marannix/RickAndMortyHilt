package com.example.rickandmorty.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.rickandmorty.R
import com.example.rickandmorty.data.network.EpisodeResponse
import kotlinx.android.synthetic.main.character_episode_item.view.*

class EpisodeListItem(
    context: Context,
    episode: EpisodeResponse
) {

    private var view: View = LayoutInflater.from(context).inflate(R.layout.character_episode_item, null, false)

    init {
        view.episodeName.text = episode.name
        view.episodeAirdate.text = episode.airdate
        view.episodeNumber.text = episode.episode
    }

    fun getView(): View {
        return view
    }
}