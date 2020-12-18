package com.example.rickandmorty.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.common.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

        setSupportActionBar(toolbar)

        setNavController()
        //setupBottomNavMenu()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }


    private fun setNavController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.destination_characters,
                R.id.destination_episodes,
                R.id.destination_favourites -> {
                    showBottomNav()
                }
                else -> hideBottomNav()
            }
        }
    }

    private fun setupBottomNavMenu() {
        bottomNavigation.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun showBottomNav() {
        bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottomNavigation.visibility = View.INVISIBLE
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        val navGraphIds = listOf(R.navigation.characters, R.navigation.favourite, R.navigation.episodes)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navigated = NavigationUI.onNavDestinationSelected(item, navController)
//        return navigated || super.onOptionsItemSelected(item)
//    }
}
