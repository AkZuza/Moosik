package com.akzuza.moosik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.akzuza.moosik.di.applicationModule
import com.akzuza.moosik.screens.home.HomeViewModel
import com.akzuza.moosik.screens.main.MainViewModel
import com.akzuza.moosik.ui.theme.MoosikTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoosikTheme {
                startKoin {
                    androidLogger()
                    androidContext(this@MainActivity)
                    modules(applicationModule)
                }

                App()
            }
        }
    }
}