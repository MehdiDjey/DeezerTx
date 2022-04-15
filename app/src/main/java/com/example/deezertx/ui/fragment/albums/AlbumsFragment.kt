package com.example.deezertx.ui.fragment.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deezertx.databinding.FragmentAlbumsBinding
import com.example.deezertx.model.Album
import com.example.deezertx.model.Albums
import com.example.deezertx.ui.adapter.AlbumsRecyclerViewAdapter
import com.example.deezertx.ui.fragment.album.BottomSheetAlbumDetails
import com.example.deezertx.utils.TAG
import com.example.deezertx.viewmodel.albums.AlbumsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


/**
 * Albums fragment
 *
 * @constructor Create empty Albums fragment
 */
class AlbumsFragment : Fragment(), AlbumsRecyclerViewAdapter.Interaction,
    SearchView.OnQueryTextListener {
    private var _biding: FragmentAlbumsBinding? = null
    private val binding get() = _biding
    private val albumsViewModel: AlbumsViewModel by sharedViewModel()
    private lateinit var albumsRecyclerViewAdapter: AlbumsRecyclerViewAdapter
    private var fetchedFullList: List<Album> = emptyList()
    private var index = 0
    private var isLoadMore = false
    private var hasNext = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _biding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupAdapter()
        setupListener()
        subscribeObserver()
        loadMoreOnReachBottom()
    }

    private fun initViews() {
        binding?.apply {
            progress.visibility = VISIBLE
            containerAlbumsFragment.visibility = GONE
            tvNothingToShow.visibility = GONE
        }
    }

    /**
     * Set the adapter
     *
     */
    private fun setupAdapter() {
        binding?.apply {
            albumsRecyclerViewAdapter = AlbumsRecyclerViewAdapter(this@AlbumsFragment)
            rvFragmentAlbumsList.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = albumsRecyclerViewAdapter
            }
        }
    }

    /**
     * Setup listener
     *
     */
    private fun setupListener() {
        binding?.svFragmentAlbums?.setOnQueryTextListener(this)
    }

    /**
     * Subscribe observer on fetch albums list data
     *
     */
    private fun subscribeObserver() {
        with(albumsViewModel) {
            albums.observe(viewLifecycleOwner) {
                if (it?.albums.isNullOrEmpty()) onEmptyOrErrorDate() else onCompleteDateSuccess(it!!)
            }

            hasNext.observe(viewLifecycleOwner) {
                this@AlbumsFragment.hasNext = it
            }
            error.observe(viewLifecycleOwner) {
                if (fetchedFullList.isNullOrEmpty()) {
                    onEmptyOrErrorDate()
                }
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Load more on reach bottom
     *
     * check if has next page and ready to load more  when user reached the bottom of list
     *
     */
    private fun loadMoreOnReachBottom() {
        binding?.rvFragmentAlbumsList?.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lifecycleScope.launch {
                        Timber.tag(TAG)
                            .d("onScrollStateChanged() called bottom is reached and has next is $hasNext")
                        if (hasNext) {
                            index += 25
                            albumsViewModel.getAllAlbums(index)
                            isLoadMore = true
                            onLoadMore()
                        }
                    }
                }
            }
        })
    }

    /**
     * On load more show the horizontal progress bar visibility
     *
     */
    private fun onLoadMore() {
        binding?.progressLoadMore?.visibility = VISIBLE
    }


    /**
     * On empty or error date show message
     *
     */
    private fun onEmptyOrErrorDate() {
        binding?.apply {
            progress.visibility = GONE
            containerAlbumsFragment.visibility = GONE
            tvNothingToShow.visibility = VISIBLE
        }
    }

    /**
     * On complete date success show result and populate list
     *
     * @param albums
     */
    private fun onCompleteDateSuccess(albums: Albums) {
        binding?.apply {
            progressLoadMore.visibility = GONE
            progress.visibility = GONE
            tvNothingToShow.visibility = GONE
            containerAlbumsFragment.visibility = VISIBLE
            if (isLoadMore) {
                val newList = albumsRecyclerViewAdapter.albums.toMutableList()
                newList.addAll(albums.albums)
                fetchedFullList = newList
                albumsRecyclerViewAdapter.albums = newList

            } else {
                albumsRecyclerViewAdapter.albums = albums.albums
                fetchedFullList = albums.albums
            }
        }
    }

    /**
     * On destroy
     * init binding value
     */
    override fun onDestroy() {
        super.onDestroy()
        _biding = null
    }

    /**
     * On album selected
     *
     * @param album
     */
    override fun onAlbumSelected(album: Album) {
        Timber.tag(TAG).d("onAlbumSelected() called with: album = $album")
        albumsViewModel.getAlbumsTracks(album.idAlbum)
        val bt = BottomSheetAlbumDetails(album)
        bt.show(childFragmentManager, bt.tag)
    }

    /**
     * On query text submit
     *
     * @param query
     * @return
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        Timber.tag(TAG).d("onQueryTextSubmit() called with: query = $query")
        updateListOnQuery(query)
        return false
    }

    /**
     * On query text change
     *
     * @param newText
     * @return
     */
    override fun onQueryTextChange(newText: String?): Boolean {
        Timber.tag(TAG).d("onQueryTextChange() called with: newText = $newText")
        updateListOnQuery(newText)
        return false
    }

    /**
     * Update list on query at runtime using DiffUtil observer according to the user input query
     *
     * @param query
     */
    private fun updateListOnQuery(query: String?) {
        if (query.isNullOrEmpty()) {
            albumsRecyclerViewAdapter.albums = fetchedFullList
        } else {
            val currentList = albumsRecyclerViewAdapter.albums
            val newList = currentList.filter {
                it.title.contains(
                    query.trim(),
                    true
                ) or it.artist.name.contains(query.trim(), true)
            }
            albumsRecyclerViewAdapter.albums = newList
        }
    }
}