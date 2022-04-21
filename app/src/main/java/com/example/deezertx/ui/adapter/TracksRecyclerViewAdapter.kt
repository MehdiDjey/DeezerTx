package com.example.deezertx.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.deezertx.R
import com.example.deezertx.databinding.ItemRowTrackBinding
import com.example.deezertx.model.Track
import com.example.deezertx.utils.AutoUpdatableAdapter
import com.example.deezertx.utils.addOnClickListener
import kotlin.properties.Delegates

class TracksRecyclerViewAdapter(
    private val interaction: Interaction,
    private val coverUrl: String
) : RecyclerView.Adapter<TracksRecyclerViewAdapter.ViewHolder>(),
    AutoUpdatableAdapter {

    var tracks: List<Track> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.idTrack == n.idTrack }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRowTrackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), interaction, coverUrl
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = tracks[position]
        holder.bind(album)
    }

    override fun getItemCount(): Int = tracks.size

    override fun getItemId(position: Int): Long {
        return tracks[position].idTrack.hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return tracks[position].idTrack
    }

    interface Interaction {
        fun onPlaySong(track: Track, position: Int)
        fun onLikeSong(track: Track, position: Int)
    }

    inner class ViewHolder(
        private val row: ItemRowTrackBinding,
        private val interaction: Interaction,
        private val coverUrl: String
    ) : RecyclerView.ViewHolder(row.root) {
        fun bind(track: Track) {

            row.apply {
                ivItemTrack.load(coverUrl) {
                    crossfade(750)
                    transformations(
                        CircleCropTransformation()
                    )
                    build()
                }
                updateItemOnLike(ivItemLike, track)
                updateItemOnPlaySong(groupRowTrack, track, tvItemTitle)
            }
        }


        private fun updateItemOnLike(ivItemLike: AppCompatImageView, track: Track) {
            ivItemLike.apply {
                setOnClickListener {
                    track.isLiked = !track.isLiked
                    val iconLikeSate = if (track.isLiked)
                        R.drawable.ic_baseline_favorite_24
                    else R.drawable.ic_baseline_favorite_border_24

                    setImageResource(iconLikeSate)
                    interaction.onLikeSong(track, bindingAdapterPosition)
                }
            }
        }

        private fun updateItemOnPlaySong(
            groupRowTrack: Group,
            track: Track,
            tvItemTitle: TextView
        ) {
            val iconPlayState = if (track.isPlayed)
                R.drawable.ic_baseline_pause_circle_outline_24
            else R.drawable.ic_baseline_play_circle_outline_24

            groupRowTrack.addOnClickListener {
                interaction.onPlaySong(track, bindingAdapterPosition)
            }
            tvItemTitle.apply {
                setCompoundDrawablesWithIntrinsicBounds(0, 0, iconPlayState, 0)
                text = track.title
            }
        }
    }
}