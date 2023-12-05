package paging

import db.Database
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface FakeApiRemoteDataSource {
    suspend fun fetchData(offset: Int, limit: Int): FakePagingItem<User>
}

class FakeApiRemoteDataSourceImpl : FakeApiRemoteDataSource {

    private val client: HttpClient = ServiceClient.fakePagingHttpClient

    override suspend fun fetchData(offset: Int, limit: Int): FakePagingItem<User> =
        client.get("https://api.slingacademy.com/v1/sample-data/users?offset=$offset&limit=$limit")
            .body()


}