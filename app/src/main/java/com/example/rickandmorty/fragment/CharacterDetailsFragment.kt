package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.rickandmorty.R
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.network.EpisodeResponse
import com.example.rickandmorty.repository.EpisodeRepository
import com.example.rickandmorty.view.EpisodeListItem
import com.example.rickandmorty.viewmodel.CharactersViewModel
import com.example.rickandmorty.viewmodel.EpisodesViewModel
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.character_header.*
import kotlinx.android.synthetic.main.character_summary.*
import kotlinx.android.synthetic.main.fragment_characters_detail.*

class CharacterDetailsFragment : Fragment() {

    private val disposables = CompositeDisposable()
    private lateinit var characters: CharactersResults
    private lateinit var episodeListItem: EpisodeListItem
    private lateinit var episodesViewModel: EpisodesViewModel
    private var shouldFetchEpisodes = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        episodesViewModel = ViewModelProviders.of(this)
            .get(EpisodesViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val safeArgs = CharacterDetailsFragmentArgs.fromBundle(it)
            characters = safeArgs.character
        }

        loadUI()
    }

    private fun loadUI() {
        loadCharacterHeader()
        loadCharacterSummary()
        loadCharacterEpisodes()
    }

    private fun loadCharacterHeader() {
        loadCharacterImage()
        loadCharacterName()
    }

    private fun loadCharacterImage() {
        Picasso.get().load(characters.image).into(characterDetailImage)
    }

    private fun loadCharacterName() {
        characterDetailName.text = characters.name
    }

    private fun loadCharacterSummary() {
        characterStatus.text = characters.status
        characterSpecies.text = characters.species
        characterGender.text = characters.gender
        characterLocation.text = characters.location.name
    }

    private fun loadCharacterEpisodes() {
        for (i in characters.episode) {
            val disposable = episodesViewModel.fetchEpisodes("$i/")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        onRetrieveEpisodesSuccess(it)
                    },
                    {
                        onRetrieveEpisodesError()
                    }
                )
            disposables.add(disposable)
        }
    }

    private fun onRetrieveEpisodesSuccess(episode: EpisodeResponse) {
        episode.characterId = characters.id
        episodesViewModel.insertEpisodes(episode)
        episodeListItem = EpisodeListItem(requireContext(), episode)
        episodeLayout.addView(episodeListItem.getView())
    }

    private fun onRetrieveEpisodesError() {
        if (shouldFetchEpisodes) {
            for (i in episodesViewModel.getEpisodes(characters.id)) {
                episodeListItem = EpisodeListItem(requireContext(), i)
                episodeLayout.addView(episodeListItem.getView())
            }
        }

        shouldFetchEpisodes = false
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }

}
