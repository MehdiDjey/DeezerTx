package com.example.deezertx.repository.albums

import com.example.deezertx.model.Albums
import com.example.deezertx.model.Tracks
import com.skydoves.sandwich.ApiResponse

interface AlbumsRepository {
    suspend fun fetchAlbums(): ApiResponse<Albums>
    suspend fun getAlbumTracks(idAlbum: Int): ApiResponse<Tracks>
}