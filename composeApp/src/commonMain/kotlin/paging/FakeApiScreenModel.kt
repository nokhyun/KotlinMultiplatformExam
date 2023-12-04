package paging

import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import org.koin.core.component.KoinComponent

class FakeApiScreenModel(
    fakePagingUseCase: FakePagingUseCase
) : ScreenModel, KoinComponent {

    val fakePagingData = fakePagingUseCase()
        .cachedIn(screenModelScope)
}