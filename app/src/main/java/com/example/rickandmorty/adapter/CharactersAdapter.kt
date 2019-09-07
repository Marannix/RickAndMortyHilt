package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.fragment.CharactersFragmentDirections
import kotlinx.android.synthetic.main.characters_item.view.*

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    private var data: List<CharactersResults> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.characters_item, parent, false))
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
            itemView.characterName.text = charactersResults.name
            itemView.characterName.setOnClickListener {
                val nextFragment = CharactersFragmentDirections.charactersToCharacterDetail()
                Navigation.findNavController(it).navigate(nextFragment)
            }
        }
    }
}