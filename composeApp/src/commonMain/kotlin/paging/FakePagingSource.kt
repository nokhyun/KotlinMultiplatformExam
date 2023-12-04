package paging

import org.koin.core.component.KoinComponent

class FakePagingSource(
    private val fakeApiRemoteDataSource: FakeApiRemoteDataSource
) : BasePagingSource<User>(), KoinComponent {

    override suspend fun fetchData(page: Int, limit: Int): FakePagingItem<User> {
        return fakeApiRemoteDataSource.fetchData(page, limit)
    }
}