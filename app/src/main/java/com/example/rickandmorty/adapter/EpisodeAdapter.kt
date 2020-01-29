package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.episodes.EpisodesResult
import kotlinx.android.synthetic.main.character_episode_item.view.*

class EpisodeAdapter : RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {

    private var data : List<EpisodesResult> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_episode_item, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data.isNotEmpty()) {
            holder.bind(data[position])
        }
    }

    fun setData(listOfEpisodes: List<EpisodesResult>) {
        data = listOfEpisodes
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        fun bind(episodes: EpisodesResult) {
            itemView.episodeName.text = episodes.name
            itemView.episodeAirdate.text = episodes.airdate
            itemView.episodeNumber.text = episodes.created
        }
    }
}