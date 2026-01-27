package com.akzuza.moosik.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(viewModel: HomeViewModel = koinViewModel()) {
    val context = LocalContext.current
    val contentResolver = context.contentResolver

    val state by viewModel.state.collectAsStateWithLifecycle()
    val allMusic = state.allMusic

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {  }
        ) {
            Text("Get All Music")
        }

        LazyColumn (
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = allMusic
            ) { music ->
                Text("Title: ${music.title} Duration: ${music.totalDurationMs}")
            }
        }
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    HomeView()
}