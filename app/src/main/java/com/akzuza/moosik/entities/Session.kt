package com.akzuza.moosik.entities

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

enum class SessionStatus {
    Empty, Play, Pause, Completed
}

data class Session @OptIn(ExperimentalTime::class) constructor(
    val title: String = "",
    val currentSongId: Int? = null,
    val status: SessionStatus = SessionStatus.Empty,
    val elapsed: Duration = Duration.ZERO,
    val maxDuration: Duration = Duration.INFINITE
) {
    fun getProgress() = (elapsed / maxDuration).toFloat()
}