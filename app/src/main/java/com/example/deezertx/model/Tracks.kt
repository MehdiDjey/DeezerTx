package com.example.deezertx.model

import android.os.Parcelable
import com.example.deezertx.utils.timeConversion
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
data class Tracks(@SerialName("data") val albums: List<Track> = emptyList()) : Parcelable {


    fun getMostPopularTitle(): Track? {
        return albums.maxByOrNull { it.rank }
    }

    fun isNotEmpty(): Boolean {
        return !albums.isNullOrEmpty()
    }

    fun getTotalDuration(): String {
        var duration = 0
        albums.forEach {
            duration += it.duration
        }
        return duration.timeConversion()
    }

    override fun toString(): String {
        return Json.encodeToString(this)
    }


}