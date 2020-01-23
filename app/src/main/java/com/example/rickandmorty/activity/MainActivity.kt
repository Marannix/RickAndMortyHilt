package com.example.rickandmorty.activity

import android.os.Bundle
import android.widget.Toast
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
        navigationView.addSpaceItem(SpaceItem("", R.drawable.ic_video_camera))
        navigationView.addSpaceItem(SpaceItem("", R.drawable.ic_video_camera))
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
                        Toast.makeText(applicationContext, "$itemIndex - $itemName Not implemented", Toast.LENGTH_SHORT)
                            .show()
                    }
                    1 -> {
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        bottomNavigationView.onSaveInstanceState(outState)
    }
}
