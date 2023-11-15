import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class MainViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _onBackPressed: MutableSharedFlow<Unit> = MutableSharedFlow(extraBufferCapacity = 1)
    val onBackPressed: SharedFlow<Unit> = _onBackPressed.asSharedFlow()

    fun onBackPressed(){
        viewModelScope.launch {
            _onBackPressed.tryEmit(Unit)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val savedStateHandle = extras.createSavedStateHandle()
                return MainViewModel(savedStateHandle) as T
            }
        }
    }
}