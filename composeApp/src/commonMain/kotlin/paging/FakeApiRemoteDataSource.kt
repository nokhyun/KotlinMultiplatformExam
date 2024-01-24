package paging

import io.ktor.client.call.body
import io.ktor.client.request.get

interface FakeApiRemoteDataSource {
    suspend fun fetchData(offset: Int, limit: Int): FakePagingItem<User>
}


class FakeApiRemoteDataSourceImpl(
    private val serviceClient: ServiceClient
) : FakeApiRemoteDataSource {

// Ktor
//    override suspend fun fetchData(offset: Int, limit: Int): FakePagingItem<User> =
//        serviceClient.fakePagingHttpClient.get("https://api.slingacademy.com/v1/sample-data/users?offset=$offset&limit=$limit")
//            .body()
    override suspend fun fetchData(offset: Int, limit: Int): FakePagingItem<User> {
        return serviceClient.ktorfitClient.fetchFake(offset, limit)
    }
}