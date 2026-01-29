package com.akzuza.moosik.screens.main
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akzuza.moosik.entities.SessionStatus
import com.akzuza.moosik.navigation.MainNavigation
import com.akzuza.moosik.navigation.NavBar
import com.akzuza.moosik.navigation.Route
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val backstack = remember { mutableStateListOf<Route>(Route.Home) }
    val status = state.session?.status ?: SessionStatus.Empty
    val play = status == SessionStatus.Play

    Scaffold (
        bottomBar = {
            NavBar(
                modifier = Modifier.fillMaxWidth(),
                destinations = listOf(Route.Home, Route.Playlist()),
                selectedDestination = backstack.last(),
                onChangeDestination = {
                    backstack.removeLastOrNull()
                    backstack.add(it)
                }
            )
        },
        topBar = {
            TopSessionBar(session = state.session)
        },
        floatingActionButton = {
            ExpandableSessionControlFAB(
                play = play,
                onPlay = { viewModel.resumeSession() },
                onPause = { viewModel.pauseSession() },
                onFastForward = {},
                onFastBackward = {}
            )
        }
    ) { innerPadding ->
        MainNavigation (
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            backstack = backstack,
            onBack = { backstack.removeLastOrNull() },
        )
    }
}

@Preview
@Composable
private fun TestHomeView(
    onStartSession: () -> Unit = {},
    onResumeSession: () -> Unit = {},
    onPauseSession: () -> Unit = {},
    onRemoveSession: () -> Unit = {},
) {
    Column {
        TextButton(
            onClick = onStartSession
        ) { Text("Start Session") }

        TextButton(
            onClick = onResumeSession
        ) { Text("Resume Session") }

        TextButton(
            onClick = onPauseSession
        ) { Text("Pause Session") }

        TextButton(
            onClick = onRemoveSession
        ) { Text("Remove Session") }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}