package com.akzuza.moosik.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akzuza.moosik.entities.Session

@Composable
fun TopSessionBar(session: Session?) {
    val title = session?.title ?: "Select a song"
    val progress = session?.getProgress() ?: 0.0f

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(40.dp)
            .padding(top = 3.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.offset(x = 8.dp),
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Monospace
        )

        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = { progress },
            strokeCap = StrokeCap.Butt,
            drawStopIndicator = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopSessionBarPreview() {
    TopSessionBar(session = null)
}