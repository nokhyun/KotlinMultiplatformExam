package paging

import com.nokhyun.kmmexam.common.cache.Favorite
import db.Database
import logger
import org.koin.core.component.KoinComponent

class FakePagingSource(
    private val fakeApiRemoteDataSource: FakeApiRemoteDataSource,
    private val database: Database
) : BasePagingSource<User>(), KoinComponent {

    override suspend fun fetchData(page: Int, limit: Int): FakePagingItem<User> {
//        logger { "allFavorite: ${database.allFavorite()[0].name}" }
//        database.createFavorite(listOf(Favorite(0, "name", "")))
        return fakeApiRemoteDataSource.fetchData(page, limit)
    }
}