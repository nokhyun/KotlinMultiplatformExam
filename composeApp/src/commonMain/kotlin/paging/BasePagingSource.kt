package paging

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FakePagingItem<Value: Any>(
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("total_users")
    val totalUsers: Int,
    @SerialName("offset")
    val offset: Int,
    @SerialName("limit")
    val limit: Int,
    @SerialName("users")
    val users: List<Value>
)

@Serializable
data class User(
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("email")
    val email: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("street")
    val street: String,
    @SerialName("state")
    val state: String,
    @SerialName("country")
    val country: String,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("id")
    val id: Int,
    @SerialName("gender")
    val gender: String,
    @SerialName("date_of_birth")
    val dateBirth: String,
    @SerialName("job")
    val job: String,
    @SerialName("city")
    val city: String,
    @SerialName("zipcode")
    val zipCode: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("profile_picture")
    val profilePicture: String
)

/** 샘플이라 베이스는 필요없긴한데 단순 참고만. */
abstract class BasePagingSource<Value: Any> : PagingSource<Int, Value>() {

    protected abstract suspend fun fetchData(page: Int, limit: Int): FakePagingItem<Value>

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        val currentPage = params.key ?: 1
        val limit = params.loadSize
        return try {
            val response = fetchData(currentPage, limit)
            PagingSourceLoadResultPage(
                data = response.users,
                prevKey = (currentPage - 1).takeIf { currentPage != 1 },
                nextKey = (currentPage + 1).takeIf { response.totalUsers >= response.users.lastIndex }
            )
        } catch (e: Exception) {
            PagingSourceLoadResultError(e)
        }
    }

}