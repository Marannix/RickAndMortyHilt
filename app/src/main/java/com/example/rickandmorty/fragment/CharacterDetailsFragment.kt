package com.example.rickandmorty.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.rickandmorty.R
import com.example.rickandmorty.data.characters.CharactersResults
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_header.*
import kotlinx.android.synthetic.main.character_summary.*
import kotlinx.android.synthetic.main.fragment_characters_detail.*

class CharacterDetailsFragment : Fragment() {

    private lateinit var characters: CharactersResults

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


}
