package com.example.rickandmorty.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.rickandmorty.R
import kotlinx.android.synthetic.main.character_episode_item.view.*

class EpisodeListItem(
    context: Context,
    episode: String
) {

    private var view: View = LayoutInflater.from(context).inflate(R.layout.character_episode_item, null, false)

    init {
        view.episode.text = episode
    }

    fun getView() : View {
        return view
    }
}