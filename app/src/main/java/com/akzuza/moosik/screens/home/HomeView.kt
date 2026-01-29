package com.akzuza.moosik.screens.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akzuza.moosik.data.OpenMultipleMusicFiles
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(viewModel: HomeViewModel = koinViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val allMusic = state.allMusic

    val launcher = rememberLauncherForActivityResult(
        contract = OpenMultipleMusicFiles(),
        onResult = { uris ->
            viewModel.addMultipleMusicFiles(uris)
        }
    )

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                launcher.launch(arrayOf("audio/*"))
            }
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