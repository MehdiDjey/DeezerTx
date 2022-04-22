package com.example.deezertx.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Precision
import com.example.deezertx.R
import com.example.deezertx.base.BaseApplication
import com.example.deezertx.databinding.ItemRowAlbumBinding
import com.example.deezertx.model.Album
import com.example.deezertx.utils.AutoUpdatableAdapter
import kotlin.properties.Delegates


class AlbumsRecyclerViewAdapter(private val interaction: Interaction) :
    RecyclerView.Adapter<AlbumsRecyclerViewAdapter.ViewHolder>(), AutoUpdatableAdapter {

    var albums: List<Album> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.idAlbum == n.idAlbum }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRowAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), interaction
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
    }

    override fun getItemCount(): Int = albums.size

    override fun getItemId(position: Int): Long {
        return albums[position].idAlbum.hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return albums[position].idAlbum
    }

    interface Interaction {
        fun onAlbumSelected(album: Album)
    }

    inner class ViewHolder(
        private val row: ItemRowAlbumBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(row.root) {
        fun bind(album: Album) {
            row.apply {
                tvAlbumTitle.apply {
                    text = album.title
                    moreOptionListener()
                }

                tvArtist.text = album.artist.name
                ivItemRow.load(album.coverBig) {
                    crossfade(750).size(500)
                    precision(Precision.EXACT)
                    placeholder(R.drawable.ic_deezer)
                    build()
                }
                itemView.setOnClickListener {
                    interaction.onAlbumSelected(album)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun TextView.moreOptionListener() {
        setOnTouchListener(OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= this.right - this.totalPaddingRight) {
                    val popup = PopupMenu(context, this)
                    popup.inflate(R.menu.menu_more_album_option)
                    popup.setOnMenuItemClickListener {
                        // TODO: do something on selected album option
                        Toast.makeText(BaseApplication.instance, "${it.title}", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    popup.show()
                    return@OnTouchListener true
                }
            }
            true
        })
    }

}