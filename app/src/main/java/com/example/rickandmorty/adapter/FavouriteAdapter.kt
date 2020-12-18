package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.characters.CharacterLocation
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.favourites.FavouriteModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_favourite_list.view.*

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    private var data: List<FavouriteModel> = emptyList()

    fun setFavouriteCharacters(favourites: List<FavouriteModel>) {
        data = favourites
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favourite_list, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data.isNotEmpty()) {
            holder.bind(data[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favourite: FavouriteModel) {
            itemView.characterName.text = favourite.name
            itemView.characterName.alpha
            Picasso.get().load(favourite.image).into(itemView.characterImage)
            itemView.characterImageCardView.setOnClickListener {
                val charactersResults =
                    CharactersResults(
                        favourite.id,
                        favourite.name,
                        favourite.status,
                        favourite.species,
                        favourite.gender,
                        favourite.image,
                        CharacterLocation(favourite.location.name),
                        favourite.episode
                    )
//                val nextFragment = FavouriteFragmentDirections.actionDestinationFavouritesToDestinationCharactersDetails(charactersResults)
//                nextFragment.character = charactersResults
//                Navigation.findNavController(it).navigate(nextFragment)
            }

        }
    }
}