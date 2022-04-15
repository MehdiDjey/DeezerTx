package com.example.deezertx.di

import com.example.deezertx.network.DeezerService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module { single { get<Retrofit>().create(DeezerService::class.java) } }