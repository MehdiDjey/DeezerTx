package com.example.deezertx.ui.fragment.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.deezertx.databinding.FragmentAlbumInfoBinding
import com.example.deezertx.model.Album
import com.example.deezertx.model.Tracks


class AlbumInfoFragment(private val album: Album, private val tracks: Tracks?) : Fragment() {
    private var _biding: FragmentAlbumInfoBinding? = null
    private val binding get() = _biding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _biding = FragmentAlbumInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi()
    }

    /**
     * Update ui
     *
     */
    private fun updateUi() {
        binding?.apply {
            if (tracks?.isNotEmpty() == true) {
                tvDuration.text = tracks.getTotalDuration()
                tvMostPopular.text = tracks.getMostPopularTitle()?.title
                tvNbFan.text = "${album.timeAdd}"
                tvNbTitle.text = "${album.nbTracks}"
                tvRelease.text = album.releaseDate
            }
        }
    }

    /**
     * On destroy
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        _biding = null
    }
}