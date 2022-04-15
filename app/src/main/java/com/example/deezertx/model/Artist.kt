package com.example.deezertx.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
data class Artist(
    @SerialName("id") val idArtist: Int,
    @SerialName("name") val name: String,
    @SerialName("picture") val picture: String,
    @SerialName("picture_small") val pictureSmall: String,
    @SerialName("picture_medium") val pictureMedium: String,
    @SerialName("picture_big") val pictureBig: String,
    @SerialName("tracklist") val trackList: String,
    @SerialName("type") val type: String,

    ) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}