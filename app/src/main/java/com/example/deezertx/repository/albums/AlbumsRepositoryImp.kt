package com.example.deezertx.repository.albums

import com.example.deezertx.model.Albums
import com.example.deezertx.model.Tracks
import com.example.deezertx.network.DeezerService
import com.example.deezertx.utils.TAG
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class AlbumsRepositoryImp(
    private val deezerService: DeezerService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AlbumsRepository {

    /**
     * Fetch albums
     *
     * @return Albums as ApiResponse
     */
    override suspend fun fetchAlbums(index: Int): ApiResponse<Albums> {
        Timber.tag(TAG).d("fetchAlbums() called")
        return withContext(dispatcher) {
            deezerService.fetchAlbumsList(index.toString())
        }
    }

    /**
     * Get album tracks
     *
     * @param idAlbum
     * @return Tracks as ApiResponse
     */
    override suspend fun getAlbumTracks(idAlbum: Int): ApiResponse<Tracks> {
        return withContext(dispatcher) {
            deezerService.fetchAlbumTracks(idAlbum)
        }

    }
}