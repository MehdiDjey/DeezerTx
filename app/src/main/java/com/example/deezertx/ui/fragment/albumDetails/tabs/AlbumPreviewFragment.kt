package com.example.deezertx.ui.fragment.albumDetails.tabs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.deezertx.databinding.FragmentAlbumPreviewBinding
import com.example.deezertx.model.Album


class AlbumPreviewFragment(private val album: Album) : Fragment() {
    private var _biding: FragmentAlbumPreviewBinding? = null
    private val binding get() = _biding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _biding = FragmentAlbumPreviewBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi()
    }

    @SuppressLint("SetTextI18n")
    /**
     *  Populate fields
     */
    private fun updateUi() {
        binding?.apply {
            ivAlbumDetail.load(album.coverBig)
            tvAlbumDetailTitle.text = album.title
            tvAlbumDetailArtist.text = "Par ${album.artist.name}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _biding = null
    }

}