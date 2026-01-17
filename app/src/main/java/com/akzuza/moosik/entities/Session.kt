package com.akzuza.moosik.entities

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

enum class SessionStatus {
    Empty, Play, Pause, Completed
}

data class Session @OptIn(ExperimentalTime::class) constructor(
    val title: String = "",
    val currentSongId: Int? = null,
    val status: SessionStatus = SessionStatus.Empty,
    val startTime: Instant
)