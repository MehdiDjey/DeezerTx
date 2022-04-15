package com.example.deezertx.di

import com.example.deezertx.repository.albums.AlbumsRepository
import com.example.deezertx.repository.albums.AlbumsRepositoryImp
import org.koin.dsl.module

/**
 * Repository module
 * Provide AlbumsRepository
 */
val repositoryModule = module { single<AlbumsRepository> { AlbumsRepositoryImp(get()) } }