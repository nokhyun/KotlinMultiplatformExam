package di

fun appModule() = listOf(
    commonModule,
    platformModule,
    fakeApiModule,
    fakeApiUseCase,
    fakePagingSource
)