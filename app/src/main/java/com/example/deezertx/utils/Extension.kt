package com.example.deezertx.utils

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast

/**
 * get tag of current current instance
 */
val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

/**
 * Time conversion
 *function used to convert and format total Int second to hh:mm:ss
 *
 * @return
 */
fun Int.timeConversion(): String {
    val MINUTES_IN_AN_HOUR = 60
    val SECONDS_IN_A_MINUTE = 60
    val seconds = this % SECONDS_IN_A_MINUTE
    val totalMinutes = this / SECONDS_IN_A_MINUTE
    val minutes = totalMinutes % MINUTES_IN_AN_HOUR
    val hours = totalMinutes / MINUTES_IN_AN_HOUR
    return "$hours:$minutes:$seconds"
}


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


/**
 * Hide  view
 *
 */
fun View.hide() {
    this.visibility = GONE
}


/**
 * Show view
 *
 */
fun View.show() {
    this.visibility = VISIBLE
}