package com.example.deezertx.ui.fragment.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.deezertx.R
import com.example.deezertx.base.BaseApplication
import com.example.deezertx.databinding.LayoutBottomSheetBinding
import com.example.deezertx.model.Album
import com.example.deezertx.model.Tracks
import com.example.deezertx.ui.adapter.DetailsViewPagerAdapter
import com.example.deezertx.utils.ViewPager2ViewHeightAnimator
import com.example.deezertx.utils.toast
import com.example.deezertx.viewmodel.albums.AlbumsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BottomSheetAlbumDetails(private val album: Album) : BottomSheetDialogFragment() {
    private var _biding: LayoutBottomSheetBinding? = null
    private val binding get() = _biding
    private val albumsViewModel: AlbumsViewModel by sharedViewModel()

    override fun onResume() {
        super.onResume()
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED

    }

    private val completeDetails = arrayOf(
        BaseApplication.instance.resources.getString(R.string.tab_title_preview),
        BaseApplication.instance.resources.getString(R.string.tab_title_details),
        BaseApplication.instance.resources.getString(R.string.tab_title_tracks)
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _biding = LayoutBottomSheetBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        subscribeObserver()
    }

    private fun subscribeObserver() {
        with(albumsViewModel) {
            tracks.observe(viewLifecycleOwner) {
                setupPagerAdapter(it)
                setupTabLayout()
            }
            error.observe(viewLifecycleOwner) {
                requireContext().toast(it)
            }
        }
    }

    private fun setupPagerAdapter(tracks: Tracks?) {
        binding?.apply {
            val adapter = DetailsViewPagerAdapter(childFragmentManager, lifecycle, album, tracks)
            viewPagerBottomSheet.adapter = adapter
            dotsIndicator.setViewPager2(viewPagerBottomSheet)
            val viewPager2ViewHeightAnimator = ViewPager2ViewHeightAnimator()
            viewPager2ViewHeightAnimator.viewPager2 = viewPagerBottomSheet
        }
    }

    private fun setupTabLayout() {
        binding?.apply {
            TabLayoutMediator(tabLayoutBottomSheet, viewPagerBottomSheet) { tab, position ->
                tab.text = completeDetails[position]
            }.attach()
        }
    }
}