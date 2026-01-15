package com.akzuza.moosik.screens.main

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun ExpandableSessionControlFAB(
    modifier: Modifier = Modifier,
    play: Boolean,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onFastForward: () -> Unit,
    onFastBackward: () -> Unit
) {
    val colors = IconButtonDefaults.filledIconButtonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    )
    val shape = ShapeDefaults.Medium
    var openMenu by remember { mutableStateOf(false) }
    val displayIcon = if (openMenu) Icons.Default.Close else
        if (play) Icons.Default.Pause else Icons.Default.PlayArrow

    val interactionSource = remember { MutableInteractionSource() }
    val scope = rememberCoroutineScope()

    var longPressJob: Job? = null

    // Launched effect to keep track of press events and trigger long press event
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    if (!openMenu) {
                        longPressJob = scope.async {
                            delay(600)
                            openMenu = true
                        }
                    }
                }

                is PressInteraction.Release -> {
                    longPressJob?.let {
                        if (it.isActive) {
                            longPressJob!!.cancel()
                        }
                    }

                    if (openMenu && longPressJob == null) openMenu = false
                    longPressJob = null
                }
            }
        }
    }

    IconButton(
        modifier = modifier,
        colors = colors,
        shape = shape,
        onClick = {
            if (!openMenu) {
                if (play) onPause()
                else onPlay()
            }
        },
        interactionSource = interactionSource
    ) {
        Icon(
            displayIcon,
            contentDescription = null,
            modifier = Modifier.scale(1.1f)
        )
    }
}

@Preview
@Composable
fun FloatingSongActionButtonPreview() {
    var play by remember { mutableStateOf(false) }
    ExpandableSessionControlFAB(
        play = play,
        onPlay = { play = true },
        onPause = { play = false },
        onFastForward = {},
        onFastBackward = {}
    )
}