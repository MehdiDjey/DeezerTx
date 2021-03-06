package com.example.deezertx.ui.fragment.albumDetails.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deezertx.databinding.FragmentAlbumTracksBinding
import com.example.deezertx.model.Album
import com.example.deezertx.model.Track
import com.example.deezertx.model.Tracks
import com.example.deezertx.ui.adapter.TracksRecyclerViewAdapter
import com.example.deezertx.utils.TAG
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import timber.log.Timber


class AlbumTracksFragment(private val album: Album, private val tracks: Tracks?) : Fragment(),
    TracksRecyclerViewAdapter.Interaction {
    private var _biding: FragmentAlbumTracksBinding? = null
    private val binding get() = _biding
    private lateinit var tracksRecyclerViewAdapter: TracksRecyclerViewAdapter
    private var currentPosition = -1
    private var exoPlayer: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _biding = FragmentAlbumTracksBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        populateTrackList()
        initPlayer()
    }

    /**
     * Init player
     *
     */
    private fun initPlayer() {
        // Create a player instance.
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
    }

    /**
     * Set url preview song and play the song
     *
     * @param track
     */
    private fun setUrlPreviewSong(track: Track) {
        // Set the media item to be played.
        exoPlayer?.setMediaItem(MediaItem.fromUri(track.preview))
        exoPlayer?.prepare()
        exoPlayer?.play()
    }

    /**
     * Stop media
     *
     */
    private fun stopMedia() {
        exoPlayer?.stop()
    }


    /**
     * Populate track list
     *
     */
    private fun populateTrackList() {
        tracksRecyclerViewAdapter.tracks = tracks?.albums ?: emptyList()
    }


    /**
     * Set the adapter
     *
     */
    private fun setupAdapter() {
        binding?.apply {
            tracksRecyclerViewAdapter =
                TracksRecyclerViewAdapter(this@AlbumTracksFragment, album.cover)
            rvTracks.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = tracksRecyclerViewAdapter
            }
        }
    }

    /**
     * On destroy
     *
     * Make sure have stop and release the exo player
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        stopMedia()
        exoPlayer?.release()
        _biding = null
    }

    /**
     * On play song
     *
     * Start the selected song
     *
     * @param track
     * @param position
     */
    override fun onPlaySong(track: Track, position: Int) {
        Timber.tag(TAG)
            .d("onPlaySong() called with: track = $track, bindingAdapterPosition = $position")
        //init state list
        val updatedList = tracksRecyclerViewAdapter.tracks.map { it.copy(isPlayed = false) }
        updatedList[position].isPlayed = true
        if (currentPosition != position && exoPlayer?.isPlaying == true) {
            stopMedia()
            setUrlPreviewSong(track)
            currentPosition = position
        } else {
            setUrlPreviewSong(track)
        }
        tracksRecyclerViewAdapter.tracks = updatedList
    }

    /**
     * On like song action
     *
     * @param track
     * @param position
     */
    override fun onLikeSong(track: Track, position: Int) {
        Timber.tag(TAG).d("onLikeSong() called with: track = $track, position = $position")
        // TODO:  do your stuff here, ex: sending  value to the ws to update track state
    }
}


