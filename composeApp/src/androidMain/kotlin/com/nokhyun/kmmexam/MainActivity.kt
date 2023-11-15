package com.nokhyun.kmmexam

import MainView
import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this) {
            // 페이지전환 스택관리 따로 만들어서 써야하나..?
//            Toast.makeText(this@MainActivity, "finish", Toast.LENGTH_SHORT).show()
//            finish()

            mainViewModel.onBackPressed()
        }

        setContent {
            MainView(
                onBackPressed = mainViewModel.onBackPressed
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    MainView()
}