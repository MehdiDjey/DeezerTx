package com.example.deezertx.network

import com.example.deezertx.model.Albums
import com.example.deezertx.model.Tracks
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DeezerService {
    @Headers("Accept: application/json")
    @GET("user/2529/albums")
    suspend fun fetchAlbumsList(): ApiResponse<Albums>


    @Headers("Accept: application/json")
    @GET("album/{idAlbum}/tracks")
    suspend fun fetchAlbumTracks(@Path("idAlbum") idAlbum: Int): ApiResponse<Tracks>
}