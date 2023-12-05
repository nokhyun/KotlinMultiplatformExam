package di

import db.Database
import db.DatabaseDriverFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import paging.FakeApiRemoteDataSource
import paging.FakeApiRemoteDataSourceImpl
import paging.FakeApiScreenModel
import paging.FakePagingSource
import paging.FakePagingUseCase

val commonModule = module {
    singleOf(::Greeting)
}

val fakeApiModule = module {
    singleOf(::FakeApiRemoteDataSourceImpl) { bind<FakeApiRemoteDataSource>() }
    singleOf(::FakeApiScreenModel)
}

val fakeApiUseCase = module {
    singleOf(::FakePagingUseCase)
}

// DatabaseDriverFactory test
val fakePagingSource = module {
    singleOf(::FakePagingSource)
    singleOf(::Database)
    singleOf(::DatabaseDriverFactory)
}