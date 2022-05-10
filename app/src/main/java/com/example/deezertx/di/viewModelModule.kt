package com.example.deezertx.di

import com.example.deezertx.ui.fragment.albumDetails.AlbumsDetailsViewModel
import com.example.deezertx.ui.fragment.albums.AlbumsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * View model module
 * Provide AlbumsViewModel
 */
val AlbumsViewModelModule = module { viewModel { AlbumsViewModel(get()) } }
val AlbumsDetailsViewModelModule = module { viewModel { AlbumsDetailsViewModel(get()) } }