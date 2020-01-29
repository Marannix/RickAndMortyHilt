package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
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
        }
    }
}