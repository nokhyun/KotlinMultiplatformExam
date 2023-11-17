package network_exam

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import logger

@Composable
fun NetworkExam() {
    logger { "[NetworkExam]" }
    val resultState: MutableState<Todos?> = remember { mutableStateOf(null) }
    val spaceXApi = remember { SpaceXApi() }

    LaunchedEffect(Unit) {
        resultState.value = withContext(Dispatchers.IO) {
//            getAllLaunches(SpaceXApi())
            getTodos(spaceXApi)
        }

        delay(3000L)

        withContext(Dispatchers.IO) {
            postTodo(spaceXApi)
        }
    }

    Text(text = resultState.value.toString())
}