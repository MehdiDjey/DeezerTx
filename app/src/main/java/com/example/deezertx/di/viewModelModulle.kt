package com.example.deezertx.di

import com.example.deezertx.viewmodel.albums.AlbumsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * View model module
 * Provide AlbumsViewModel
 */
val viewModelModule = module { viewModel { AlbumsViewModel(get()) } }