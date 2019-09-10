package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.network.EpisodeResponse
import kotlinx.android.synthetic.main.character_episode_item.view.*

class EpisodeAdapter : RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {

    private var episodes: List<EpisodeResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.character_episode_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (episodes.isNotEmpty()) {
            holder.bind(episodes[position])
        }
    }

    fun setEpisodes(episodeResponse: List<EpisodeResponse>) {
        episodes = episodeResponse
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(episode: EpisodeResponse) {
            itemView.episodeName.text = episode.name
            itemView.episodeAirdate.text = episode.airdate
            itemView.episodeNumber.text = episode.episode
        }
    }
}