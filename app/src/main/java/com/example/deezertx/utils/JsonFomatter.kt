package com.example.deezertx.utils

import kotlinx.serialization.json.Json


/**
 *  json formatter to prevent unknown attribute
 */
val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
    encodeDefaults = false
}