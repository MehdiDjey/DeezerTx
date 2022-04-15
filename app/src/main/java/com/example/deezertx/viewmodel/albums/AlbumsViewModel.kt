package com.example.deezertx.viewmodel.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezertx.`interface`.IOnAlbum
import com.example.deezertx.model.Albums
import com.example.deezertx.model.Tracks
import com.example.deezertx.repository.albums.AlbumsRepository
import com.example.deezertx.utils.TAG
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.launch
import timber.log.Timber

class AlbumsViewModel(private val repository: AlbumsRepository) : ViewModel(), IOnAlbum {
    private var _albums: MutableLiveData<Albums?> = MutableLiveData()
    val albums: LiveData<Albums?> = _albums


    private var _tracks: MutableLiveData<Tracks?> = MutableLiveData()
    val tracks: LiveData<Tracks?> = _tracks

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    /**
     * fetch albums list data from network api call
     *
     */
    override fun getAllAlbums() {
        viewModelScope.launch {
            val response = repository.fetchAlbums()
            // handle the case when the api request gets a success response
            response.onSuccess {
                Timber.tag(TAG).d("getAllAlbums() called with data : $data")
                _albums.value = data

            }
                // handle the case when the api request gets a error response
                // e.g BadGateWay
                .onError {
                    Timber.tag(TAG).d("getAllAlbums() called with message error ${message()}")
                    _error.value = "$statusCode(${statusCode.code}): ${message()}"

                }
                // handle the case when the API request gets a exception response.
                // e.g. network connection error.
                .onException {
                    Timber.tag(TAG).d("getAllAlbums() called with message error ${message()}")
                    _error.value = message()
                }
        }

    }

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

