package com.example.rickandmorty.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.example.rickandmorty.R

class FullscreenLoadingDialog(context: Context) : Dialog(context, R.style.FullscreenLoading) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setContentView(R.layout.fullscreen_layout)
    }
}