package com.akzuza.moosik.screens.main

import com.akzuza.moosik.entities.Music
import com.akzuza.moosik.entities.Session

data class MainState (
    val session: Session? = null,
    val queue: List<Music> = emptyList()
)