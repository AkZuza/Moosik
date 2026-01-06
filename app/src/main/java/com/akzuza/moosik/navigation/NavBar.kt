package com.akzuza.moosik.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

private val routeIconMap = mapOf<Route, ImageVector>(
    Route.Home to Icons.Default.Home,
    Route.Playlist() to Icons.Default.Refresh
)

@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    destinations: List<Route>,
    selectedDestination: Route,
    onChangeDestination: (Route) -> Unit
) {
    NavigationBar (
        modifier = modifier
    ) {
        destinations.forEach { route ->
            NavigationBarItem(
                selected = selectedDestination == route,
                onClick = {
                    if (selectedDestination != route)
                        onChangeDestination(route)
                },
                label = { Text(route.name) },
                icon = { routeIconMap[route]?.let { Icon(imageVector = it, contentDescription = null) } }
            )
        }
    }
}

@Preview
@Composable
fun NavBarPreview() {
    val destinations = listOf(Route.Home, Route.Playlist())
    NavBar(
        destinations = destinations,
        selectedDestination = Route.Home,
        onChangeDestination = {},
    )
}