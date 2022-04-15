package com.example.deezertx.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.deezertx.R
import com.example.deezertx.databinding.ItemRowTrackBinding
import com.example.deezertx.model.Track
import com.example.deezertx.utils.AutoUpdatableAdapter
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
        fun onPlaySong(track: Track, bindingAdapterPosition: Int)
    }

    inner class ViewHolder(
        private val row: ItemRowTrackBinding,
        private val interaction: Interaction,
        private val coverUrl: String
    ) : RecyclerView.ViewHolder(row.root) {
        fun bind(track: Track) {
            row.apply {
                ivItemTrack.load(coverUrl) {
                    placeholder(R.drawable.ic_deezer)
                    crossfade(750)
                    transformations(
                        CircleCropTransformation()
                    )
                    build()
                }
                tvItemTitle.text = track.title
                itemView.setOnClickListener {
                    interaction.onPlaySong(track, bindingAdapterPosition)
                }
            }
        }

    }

}