package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.rickandmorty.R
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.repository.EpisodeRepository
import com.example.rickandmorty.view.EpisodeListItem
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
            val disposable = EpisodeRepository().fetchCharacterEpisodes("$i/")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        onRetrieveEpisodesSuccess(it.episode)
                    },
                    {
                        onRetrieveEpisodesError(it.message)
                    }
                )
            disposables.add(disposable)
        }
    }

    private fun onRetrieveEpisodesSuccess(episode: String) {
        episodeListItem = EpisodeListItem(requireContext(), episode)
        episodeLayout.addView(episodeListItem.getView())
    }

    private fun onRetrieveEpisodesError(message: String?) {
        Log.e("Error", message)
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }

}
