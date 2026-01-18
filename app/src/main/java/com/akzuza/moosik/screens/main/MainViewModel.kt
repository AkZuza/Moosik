package com.akzuza.moosik.screens.main

import androidx.lifecycle.ViewModel
import com.akzuza.moosik.entities.Session
import com.akzuza.moosik.entities.SessionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

class MainViewModel : ViewModel() {
    private var _state = MutableStateFlow(MainState(session = Session()))
    val state: StateFlow<MainState> = _state

    // Session control
    fun startSession() {
        if (state.value.session != null) {
            _state.update {
                it.copy(session = Session())
            }
        }

        val session = state.value.session!!
        updateSessionStatus(SessionStatus.Play)
    }
    fun pauseSession() {
        if (state.value.session == null) return
        updateSessionStatus(SessionStatus.Pause)
    }

    fun resumeSession() {
        if (state.value.session == null) return
        if (state.value.session?.status == SessionStatus.Empty) return
        updateSessionStatus(SessionStatus.Play)
    }

    fun removeSession() {
        if (state.value.session != null) {
            updateSessionStatus(SessionStatus.Empty)
            _state.update { it.copy(session = null) }
        }
    }

    fun updateSession() {

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