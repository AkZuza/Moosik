package com.akzuza.moosik.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.akzuza.moosik.navigation.NavBar
import com.akzuza.moosik.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val backstack = remember { mutableStateListOf<Any>(Route.Home) }
    var play by remember { mutableStateOf(false) }

    val songText = remember(play) {
        if (play) "Song is playing"
        else "Song is not playing"
    }

    Scaffold (
        bottomBar = {
            NavBar(
                modifier = Modifier.fillMaxWidth(),
                destinations = listOf(Route.Home, Route.Playlist()),
                selectedDestination = backstack.last() as Route,
                onChangeDestination = {
                    backstack.removeLastOrNull()
                    backstack.add(it)
                }
            )
        },
        topBar = {
            TopSessionBar(
                songName = songText,
                progress = 0.6f
            )
        },
        floatingActionButton = {
            FloatingSongActionButton(
                modifier = Modifier.scale(1.5f).offset(x =(-10).dp, y = (-10).dp),
                play = play,
                onClick = { play = !play },
                onLeftSwipe = {},
                onRightSwipe = {}
            )
        }
    ) { innerPadding ->
        NavDisplay (
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            backStack = backstack,
            onBack = { backstack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<Route.Home> {  }
                entry<Route.Playlist> {  }
            }
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}