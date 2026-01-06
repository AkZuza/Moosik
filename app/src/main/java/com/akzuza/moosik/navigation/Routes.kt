package com.akzuza.moosik.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route (val name: String) {
    object Home : Route("Home")
    data class Playlist (val playlist: String? = null): Route("Playlist")
}