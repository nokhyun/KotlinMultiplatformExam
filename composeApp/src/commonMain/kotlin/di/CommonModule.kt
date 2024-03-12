package di

import db.Database
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import paging.FakeApiRemoteDataSource
import paging.FakeApiRemoteDataSourceImpl
import paging.FakeApiScreenModel
import paging.FakeDetailScreenModel
import paging.FakePagingSource
import paging.FakePagingUseCase
import paging.ServiceClient

val commonModule = module {
    singleOf(::Greeting)
    single { ServiceClient }
}

val fakeApiModule = module {
    singleOf(::FakeApiRemoteDataSourceImpl) { bind<FakeApiRemoteDataSource>() }
    factory { FakeApiScreenModel(get()) }
    factory { FakeDetailScreenModel() }
}

val fakeApiUseCase = module {
    singleOf(::FakePagingUseCase)
}

val fakePagingSource = module {
    singleOf(::FakePagingSource)
    singleOf(::Database)
}

expect val sqldelightModule: Module