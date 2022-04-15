package com.example.deezertx.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Parcelize
@Serializable
data class Album(
    @SerialName("id") val idAlbum: Int,
    @SerialName("title") val title: String,
    @SerialName("cover") val cover: String,
    @SerialName("cover_small") val coverSmall: String,
    @SerialName("cover_medium") val coverMedium: String,
    @SerialName("cover_big") val coverBig: String,
    @SerialName("nb_tracks") val nbTracks: Int,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("record_type") val recordType: String,
    @SerialName("available") val isAvailable: Boolean,
    @SerialName("time_add") val timeAdd: Int,
    @SerialName("type") val type: String,
    @SerialName("artist") val artist: Artist,

    ) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}
