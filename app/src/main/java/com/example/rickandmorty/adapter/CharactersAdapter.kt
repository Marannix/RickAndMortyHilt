package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.fragment.CharactersFragmentDirections
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_characters.view.*

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    private var data: List<CharactersResults> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_characters, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data.isNotEmpty()) {
            holder.bind(data[position])
        }
    }

    fun setData(charactersResults: List<CharactersResults>) {
        data = charactersResults
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(charactersResults: CharactersResults) {
            itemView.characterId.text = "#${charactersResults.id}"
            itemView.characterName.text = charactersResults.name
            itemView.characterStatus.text = charactersResults.status
            itemView.characterSpecies.text = charactersResults.species
            itemView.characterLocation.text = charactersResults.location.name

            Picasso.get().load(charactersResults.image).into(itemView.characterImage)

            itemView.characterConstraintLayout.setOnClickListener {
                val nextFragment = CharactersFragmentDirections.charactersToCharacterDetail(charactersResults)
                nextFragment.character = charactersResults
                Navigation.findNavController(it).navigate(nextFragment)
            }
        }
    }
}