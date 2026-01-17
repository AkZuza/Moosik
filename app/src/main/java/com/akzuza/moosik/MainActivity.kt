package com.akzuza.moosik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.akzuza.moosik.di.applicationModule
import com.akzuza.moosik.ui.theme.MoosikTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

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