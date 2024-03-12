package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatform

abstract class BaseScreen : Screen {

    @Composable
    fun Toolbar(
        title: String,
        onBack: (() -> Unit)? = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            if (onBack != null) {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterStart)
                        .clickable { onBack?.invoke() },
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                text = title,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    inline fun <reified T : ScreenModel> getScreenModel(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null,
    ): T {
        val koin = KoinPlatform.getKoin()
        return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
    }
}