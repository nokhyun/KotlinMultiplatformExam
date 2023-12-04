package paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class FakePagingUseCase(
    private val fakeApiPageSource: FakePagingSource
) {

    operator fun invoke(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = { fakeApiPageSource }
        ).flow
    }
}