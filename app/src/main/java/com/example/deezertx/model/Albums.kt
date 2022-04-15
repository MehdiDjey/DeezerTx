package com.example.deezertx.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
data class Albums(
    @SerialName("data") val albums: List<Album> = emptyList(),
    @SerialName("next") val nextAlbums: String? = ""

) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}