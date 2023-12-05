package di

/** 각 플랫폼에서 사용할 모듈들. */
fun appModule() = listOf(
    commonModule,
    platformModule,
    fakeApiModule,
    fakeApiUseCase,
    fakePagingSource
)