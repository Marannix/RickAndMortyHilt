package com.example.rickandmorty.activity

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.rickandmorty.R
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceNavigationView
import com.luseen.spacenavigation.SpaceOnClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var navigationView: SpaceNavigationView
    private var currentSelectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navigationView = findViewById(R.id.bottomNavigationView)

        initBottomNavigationView(savedInstanceState)
    }

    private fun initBottomNavigationView(savedInstanceState: Bundle?) {
        addItemToBottomNavigationView(savedInstanceState)
        setNavigationViewClickListener()
    }

    private fun addItemToBottomNavigationView(savedInstanceState: Bundle?) {
        navigationView.initWithSaveInstanceState(savedInstanceState)
        navigationView.addSpaceItem(SpaceItem("", R.drawable.ic_character_girl))
        navigationView.addSpaceItem(SpaceItem("", R.drawable.ic_episode_list))
        navigationView.addSpaceItem(SpaceItem("", R.drawable.ic_characters_icon))
    }

    private fun setNavigationViewClickListener() {
        navigationView.setSpaceOnClickListener(object : SpaceOnClickListener {
            override fun onCentreButtonClick() {
                navigationView.setCentreButtonSelectable(true)
                navigationView.setCentreButtonSelected()
            }

            override fun onItemReselected(itemIndex: Int, itemName: String?) {

            }

            override fun onItemClick(itemIndex: Int, itemName: String?) {
                when (itemIndex) {
                    0 -> {
                        currentSelectedItem = 0
                        onCharactersListSelected()
                        Toast.makeText(applicationContext, "$itemIndex - $itemName Not implemented", Toast.LENGTH_SHORT)
                            .show()
                    }
                    1 -> {
                        currentSelectedItem = 1
                        onEpisodeFragmentSelected()
                        Toast.makeText(applicationContext, "$itemIndex - $itemName Not implemented", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        Toast.makeText(applicationContext, "$itemIndex - $itemName Not implemented", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }


    fun onCharactersListSelected() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.navigate(R.id.destination_characters)
    }

    fun onEpisodeFragmentSelected() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        // val nextFragment = CharactersFragmentDirections.actionDestinationCharactersToEpisodesFragment()
        navController.navigate(R.id.action_destination_characters_to_episodesFragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        bottomNavigationView.onSaveInstanceState(outState)
    }
    
}
