package paging

import cafe.adriel.voyager.core.model.ScreenModel
import logger
import org.koin.core.component.KoinComponent

class FakeDetailScreenModel : ScreenModel, KoinComponent {

    override fun onDispose() {
        super.onDispose()
        logger { "FakeDetailScreenModel onDispose" }
    }
}