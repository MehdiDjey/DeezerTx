package com.example.deezertx.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.deezertx.databinding.ActivityMainBinding
import com.example.deezertx.viewmodel.albums.AlbumsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Main activity class. Not much happens here, just some basic UI setup.
 * The main logic occurs in the fragments which populate this activity.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var biding: ActivityMainBinding
    private val albumsViewModel: AlbumsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = ActivityMainBinding.inflate(layoutInflater)
        biding.apply {
            setContentView(root)
            getAlbums()
        }
    }

    private fun getAlbums() {
        albumsViewModel.getAllAlbums(0)
    }
}