package db

import com.nokhyun.kmmexam.common.cache.AppDatabase
import com.nokhyun.kmmexam.common.cache.Favorite

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllFavorite()
        }
    }

    internal fun allFavorite(): List<Favorite> =
        dbQuery.selectFavorite(::mapperFavoriteSelecting).executeAsList()

    internal fun createFavorite(favorites: List<Favorite>) {
        dbQuery.transaction {
            favorites.forEach {
                insertFavorite(it)
            }
        }
    }

    private fun insertFavorite(favorite: Favorite) {
        dbQuery.insertFavorite(
            num = favorite.num,
            name = favorite.name,
            profileUrl = favorite.profileUrl
        )
    }

    private fun mapperFavoriteSelecting(num: Long, name: String, profileUrl: String) =
        Favorite(num = num, name = name, profileUrl = profileUrl)
}