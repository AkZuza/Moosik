package com.akzuza.moosik.screens.home

import com.akzuza.moosik.entities.Music

data class HomeState (
    val allMusic: List<Music> = emptyList()
)