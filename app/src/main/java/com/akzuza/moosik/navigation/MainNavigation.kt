package com.akzuza.moosik.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.akzuza.moosik.screens.home.HomeView

@Composable
fun MainNavigation(modifier: Modifier = Modifier, backstack: SnapshotStateList<Route>, onBack: () -> Unit) {
    NavDisplay (
        modifier = modifier,
        backStack = backstack,
        onBack = { onBack() },
        entryProvider = entryProvider {
            entry<Route.Home> { HomeView() }
            entry<Route.Playlist> {  }
        }
    )
}