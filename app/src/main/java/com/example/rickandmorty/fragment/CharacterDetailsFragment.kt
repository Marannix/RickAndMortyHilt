package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.CharacterEpisodeAdapter
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.viewmodel.EpisodesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.character_header.*
import kotlinx.android.synthetic.main.character_summary.*
import kotlinx.android.synthetic.main.fragment_characters_detail.*

class CharacterDetailsFragment : BaseFragment() {

    private val viewModel: EpisodesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(EpisodesViewModel::class.java)
    }

    private val characterEpisodesAdapter = CharacterEpisodeAdapter()
    private lateinit var character: CharactersResults

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouriteButton.init(activity)
        arguments?.let {
            val safeArgs = CharacterDetailsFragmentArgs.fromBundle(it)
            character = safeArgs.character
        }
        viewModel.getEpisodes(character)
        loadUI()
    }

    private fun loadUI() {
        updateToolbar()
        loadCharacterHeader()
        loadCharacterSummary()
        setEpisodesAdapter()
        loadCharacterEpisodes()
        setFavouriteButton()
    }

    private fun loadCharacterHeader() {
        loadCharacterImage()
        loadCharacterName()
    }

    private fun setFavouriteButton() {
        setFavouriteButtonIcon()
        setFavouriteButtonListener()
    }

    private fun setFavouriteButtonIcon() {
        favouriteButton.isChecked = viewModel.isFavourite(character.id)
    }

    private fun loadCharacterImage() {
        Picasso.get().load(character.image).into(characterDetailImage)
    }

    private fun setFavouriteButtonListener() {
        favouriteButton.setOnClickListener {
            when {
                viewModel.isFavourite(character.id) -> removeFromFavourite()
                else -> addToFavourite()
            }
        }
    }

    private fun addToFavourite() {
        viewModel.insertFavourite(character)
    }

    private fun removeFromFavourite() {
        viewModel.removeFromFavourites(character)
    }

    private fun loadCharacterName() {
        characterDetailName.text = character.name
    }

    private fun loadCharacterSummary() {
        characterStatus.text = character.status
        characterSpecies.text = character.species
        characterGender.text = character.gender
        characterLocation.text = character.location.name
    }

    private fun setEpisodesAdapter() {
        episodesRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        episodesRecyclerView.adapter = characterEpisodesAdapter
    }

    private fun loadCharacterEpisodes() {
        viewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                EpisodesViewModel.ViewState.Loading -> {
                    // Show Loading indicator
                }
                is EpisodesViewModel.ViewState.Content -> {
                    characterEpisodesAdapter.setEpisodes(viewState.listOfCharacterEpisodes)
                }
                is EpisodesViewModel.ViewState.Error -> {
                    Log.d(
                        "I don't have content",
                        viewState.message
                    )
                }
            }
        })
    }

    private fun updateToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.toolbar_overview_title)
    }

}
