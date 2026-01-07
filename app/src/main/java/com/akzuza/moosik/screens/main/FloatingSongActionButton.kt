package com.akzuza.moosik.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FloatingSongActionButton(
    modifier: Modifier = Modifier,
    play: Boolean,
    onClick: () -> Unit,
    onLeftSwipe: () -> Unit,
    onRightSwipe: () -> Unit
) {
    val colors = IconButtonDefaults.filledIconButtonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    )

    val shape = ShapeDefaults.Medium

    IconButton(
        modifier = modifier,
        onClick = onClick,
        colors = colors,
        shape = shape
    ) {
        val icon = if (!play) Icons.Default.PlayArrow else Icons.Default.Pause
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.scale(1.1f)
        )
    }
}

@Preview
@Composable
fun FloatingSongActionButtonPreview() {
    var play by remember { mutableStateOf(false) }
    FloatingSongActionButton(
        play = play,
        onClick = { play = !play},
        onLeftSwipe = {},
        onRightSwipe = {},
    )
}