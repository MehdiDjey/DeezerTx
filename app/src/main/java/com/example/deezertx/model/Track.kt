package com.example.deezertx.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
data class Track(
    @SerialName("id") val idTrack: Int,
    @SerialName("readable") val isReadable: Boolean,
    @SerialName("title") val title: String,
    @SerialName("duration") val duration: Int,
    @SerialName("rank") val rank: Int,
    @SerialName("type") val type: String,
    @SerialName("preview") val preview: String,
    var isPlayed: Boolean = false
) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}