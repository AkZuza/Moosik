package com.akzuza.moosik.entities

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Session @OptIn(ExperimentalTime::class) constructor(
    val currentSongId: Int? = null,
    val startTime: Instant
)