package com.example.deezertx.ui.fragment.albumDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezertx.`interface`.IOnAlbum
import com.example.deezertx.model.Tracks
import com.example.deezertx.repository.albums.AlbumsRepository
import com.example.deezertx.utils.TAG
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.launch
import timber.log.Timber

class AlbumsDetailsViewModel(private val repository: AlbumsRepository) : ViewModel(), IOnAlbum {

    private var _tracks: MutableLiveData<Tracks?> = MutableLiveData()
    val tracks: LiveData<Tracks?> = _tracks


    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    /**
     * Get all tracks of selected album according to the id
     *
     * @param idAlbum of the current selected album
     */
    override fun getAlbumsTracks(idAlbum: Int) {
        Timber.tag(TAG).d("getAlbumsTracks() called with: idAlbum = $idAlbum")
        viewModelScope.launch {
            val response = repository.getAlbumTracks(idAlbum)
            // handle the case when the api request gets a success response
            response.onSuccess {
                Timber.tag(TAG).d("getAlbumsTracks() called with data : $data")
                _tracks.value = data

            }
                // handle the case when the api request gets a error response
                // e.g BadGateWay
                .onError {
                    Timber.tag(TAG).d("getAlbumsTracks() called with message error ${message()}")
                    _error.value = "$statusCode(${statusCode.code}): ${message()}"

                }
                // handle the case when the API request gets a exception response.
                // e.g. network connection error.
                .onException {
                    Timber.tag(TAG).d("getAlbumsTracks() called with message error ${message()}")
                    _error.value = message()
                }
        }
    }
}