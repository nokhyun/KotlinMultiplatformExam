package com.nokhyun.kmmexam

import MainView
import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.resourcesFetcher

@OptIn(InternalVoyagerApi::class)
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

        setContent {
            val kamelConfig = remember {
                KamelConfig {
                    takeFrom(KamelConfig.Default)
                    resourcesFetcher(this@MainActivity)
                }
            }

            MainView(kamelConfig)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    MainView()
}