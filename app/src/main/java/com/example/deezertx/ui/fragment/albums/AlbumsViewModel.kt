package com.example.deezertx.ui.fragment.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezertx.`interface`.IOnAlbums
import com.example.deezertx.model.Albums
import com.example.deezertx.repository.albums.AlbumsRepository
import com.example.deezertx.utils.TAG
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.launch
import timber.log.Timber

class AlbumsViewModel(private val repository: AlbumsRepository) : ViewModel(), IOnAlbums {

    private var _albums: MutableLiveData<Albums?> = MutableLiveData()
    val albums: LiveData<Albums?> = _albums

    private var _hasNext: MutableLiveData<Boolean> = MutableLiveData()
    val hasNext: LiveData<Boolean> = _hasNext

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    /**
     * fetch albums list data from network api call
     *
     */
    override fun getAllAlbums(index: Int) {
        viewModelScope.launch {
            val response = repository.fetchAlbums(index)
            // handle the case when the api request gets a success response
            response.onSuccess {
                Timber.tag(TAG).d("getAllAlbums() called with data : $data")
                _hasNext.value = !data.nextAlbums.isNullOrEmpty()
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

}

