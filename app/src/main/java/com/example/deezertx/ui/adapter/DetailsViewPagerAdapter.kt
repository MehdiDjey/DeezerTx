package com.example.deezertx.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.deezertx.model.Album
import com.example.deezertx.model.Tracks
import com.example.deezertx.ui.fragment.album.AlbumInfoFragment
import com.example.deezertx.ui.fragment.album.AlbumPreviewFragment
import com.example.deezertx.ui.fragment.album.AlbumTracksFragment

private const val NUM_TABS = 3
private const val NUM_TABS_ON_EMPTY_DATA = 1

class DetailsViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val album: Album,
    private val tracks: Tracks?
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return if (tracks?.isNotEmpty() == true) NUM_TABS else NUM_TABS_ON_EMPTY_DATA
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AlbumPreviewFragment(album)
            1 -> return AlbumInfoFragment(album, tracks)
            2 -> return AlbumTracksFragment(album, tracks)
        }
        return AlbumPreviewFragment(album)
    }
}