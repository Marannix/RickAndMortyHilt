package com.example.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rickandmorty.data.network.ApiService
import com.example.rickandmorty.repository.CharactersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val FIRST_PAGE = 1

class MainActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val disposable = CharactersRepository().fetchCharacters(FIRST_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {Toast.makeText(this, it.charactersResults[1].id, Toast.LENGTH_SHORT).show()},
                {Toast.makeText(this, "yikes", Toast.LENGTH_SHORT).show()}
            )

        disposables.add(disposable)
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}
