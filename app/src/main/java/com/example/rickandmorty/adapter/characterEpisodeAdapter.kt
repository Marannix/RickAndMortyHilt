package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.characters.CharacterEpisodeResponse
import kotlinx.android.synthetic.main.character_episode_item.view.*

class CharacterEpisodeAdapter : RecyclerView.Adapter<CharacterEpisodeAdapter.ViewHolder>() {

    private var characterEpisodes: List<CharacterEpisodeResponse> = emptyList()

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
        return characterEpisodes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (characterEpisodes.isNotEmpty()) {
            holder.bind(characterEpisodes[position])
        }
    }

    fun setEpisodes(characterEpisodeResponse: List<CharacterEpisodeResponse>) {
        characterEpisodes = characterEpisodeResponse
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(characterEpisode: CharacterEpisodeResponse) {
            itemView.episodeName.text = characterEpisode.name
            itemView.episodeAirdate.text = characterEpisode.airdate
            itemView.episodeNumber.text = characterEpisode.episode
        }
    }
}