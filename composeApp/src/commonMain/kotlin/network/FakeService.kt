package network

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import paging.FakePagingItem
import paging.User

interface FakeService {

    @GET("v1/sample-data/users")
    suspend fun fetchFake(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): FakePagingItem<User>
}