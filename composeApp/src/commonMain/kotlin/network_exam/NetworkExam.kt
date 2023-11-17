package network_exam

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import logger

@Composable
fun NetworkExam(){
    val coroutineScope = rememberCoroutineScope()
    val resultState: MutableState<Todos?> = remember { mutableStateOf(null) }
    coroutineScope.launch {
        val result = withContext(Dispatchers.IO){
//            getAllLaunches(SpaceXApi())
            getTodos(SpaceXApi())
        }

        resultState.value = result
    }

    Text(text = resultState.value.toString())
}