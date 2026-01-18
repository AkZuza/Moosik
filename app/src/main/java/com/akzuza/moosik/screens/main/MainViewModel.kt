package com.akzuza.moosik.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akzuza.moosik.entities.Music
import com.akzuza.moosik.entities.Session
import com.akzuza.moosik.entities.SessionStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

class MainViewModel : ViewModel() {
    private var _state = MutableStateFlow(MainState(
        session = Session(
            status = SessionStatus.Pause,
            maxDuration = Duration.parse("10s")
        )
    ))
    val state: StateFlow<MainState> = _state
    private var job: Job = Job()

    // Session control
    fun startSession() {
        if (state.value.session != null) {
            _state.update {
                it.copy(session = Session())
            }
        }
        updateSessionStatus(SessionStatus.Play)
        startCountingElapsedDuration()
    }
    fun pauseSession() {
        if (state.value.session == null) return
        updateSessionStatus(SessionStatus.Pause)
        stopCountingElapsedDuration()
    }

    fun resumeSession() {
        if (state.value.session == null) return
        if (state.value.session?.status == SessionStatus.Empty) return
        updateSessionStatus(SessionStatus.Play)
        startCountingElapsedDuration()
    }

    fun removeSession() {
        if (state.value.session == null) return
        stopCountingElapsedDuration()
        updateSessionStatus(SessionStatus.Empty)
        _state.update { it.copy(session = null) }
    }

    fun updateSession() {

    }

    fun addMusicToQueue(newMusic: Music) {
        _state.update {
            it.copy(queue = it.queue + newMusic)
        }
    }

    private fun startCountingElapsedDuration() {
        if (state.value.session == null) return
        job = viewModelScope.launch { elapsedDurationCounter() }
    }

    private fun stopCountingElapsedDuration() {
        if (state.value.session == null) return
        job.cancel()
    }

    private suspend fun elapsedDurationCounter() {
        val delayTime = 100L
        val startMark = TimeSource.Monotonic.markNow()
        val startDuration = state.value.session?.elapsed!!
        val maxDuration = state.value.session?.maxDuration!!

        var latestDuration = startDuration
        while (latestDuration <= maxDuration) {
            val elapsedNow = startMark.elapsedNow()
            latestDuration = startDuration + elapsedNow
            updateSessionElapsedDuration(latestDuration)
            delay(delayTime)
        }
    }

    private fun updateSessionElapsedDuration(newDuration: Duration) {
        if (state.value.session == null) return
        val session = state.value.session!!
        _state.update {
            it.copy(session = session.copy(elapsed = newDuration))
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun updateSessionStatus(newStatus: SessionStatus) {
        if (state.value.session == null) return
        val session: Session = state.value.session!!

        if (session.status != newStatus) {
            _state.update {
                it.copy(
                    session = session.copy(status = newStatus)
                )
            }
        }
    }
}