package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.EpisodeAdapter
import com.example.rickandmorty.data.characters.CharactersResults
import com.example.rickandmorty.data.network.EpisodeResponse
import com.example.rickandmorty.viewmodel.EpisodesViewModel
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.android.synthetic.main.character_header.*
import kotlinx.android.synthetic.main.character_summary.*
import kotlinx.android.synthetic.main.fragment_characters_detail.*
import javax.inject.Inject

class CharacterDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: EpisodesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(EpisodesViewModel::class.java)
    }

    private val disposables = CompositeDisposable()
    private val episodesAdapter = EpisodeAdapter()

    private lateinit var character: CharactersResults
    private val episodes = ArrayList<EpisodeResponse>()

    private fun selector(episode: EpisodeResponse): Int = episode.id.toInt()

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
        handleUndeliverableException()
        loadUI()
    }

    private fun handleUndeliverableException() {
        //Added due to -  2 exceptions occurred. which causes a crash
        RxJavaPlugins.setErrorHandler { e ->
            var error = ""
            if (e is UndeliverableException) {
                error = e.cause.toString()
            }
            Log.e("Whats going on", error)

        }
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
        episodesRecyclerView.adapter = episodesAdapter
    }

    private fun loadCharacterEpisodes() {
        // TODO: Put this in viewmodel ;)
        viewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                EpisodesViewModel.ViewState.Loading -> {
                    // Show Loading indicator
                }
                is EpisodesViewModel.ViewState.Content -> {
                    Log.d(
                        "I have content",
                        viewState.listOfEpisodes.also { viewState.listOfEpisodes.sortedBy { selector(it) }}.toString()
                    )
                }
                is EpisodesViewModel.ViewState.Error -> {
                    Log.d(
                        "I don't have content",
                        viewState.message
                    )
                }
            }
        })
//        viewModel.stuff(character.id, character.episode)
//        for (i in character.episode) {
//            val disposable = viewModel.fetchEpisodes("$i/")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    {
//                        onRetrieveEpisodesSuccess(it)
//                    },
//                    {
//                        onRetrieveEpisodesError()
//                    }
//                )
//            disposables.add(disposable)
//        }
    }

    private fun onRetrieveEpisodesSuccess(episode: EpisodeResponse) {
        // This should be handled in the viewmodel
        episode.characterId = character.id
        episodes.add(
            EpisodeResponse(
                episode.episodeId,
                episode.characterId,
                episode.id,
                episode.name,
                episode.airdate,
                episode.episode
            )
        ).also {
            episodes.sortBy { selector(it) }
        }
        episodesAdapter.setEpisodes(episodes)
     //  viewModel.insertEpisodes(episode)
    }
//
//    private fun onRetrieveEpisodesError() {
//        if (shouldFetchEpisodes) {
//            episodesAdapter.setEpisodes(viewModel.getEpisodes(character.id))
//        }
//        shouldFetchEpisodes = false
//    }

    private fun updateToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.toolbar_overview_title)
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }

}
