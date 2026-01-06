package com.akzuza.moosik.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.akzuza.moosik.navigation.NavBar
import com.akzuza.moosik.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val backstack = remember { mutableStateListOf<Any>(Route.Home) }
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
                songName = "A Song will play here",
                progress = 0.6f
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