package network_exam

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.reflect.TypeInfo
import kotlinx.serialization.json.Json
import logger

suspend fun getAllLaunches(spaceXApi: SpaceXApi): List<RocketLaunch> {
    return spaceXApi.httpClient.get("https://api.spacexdata.com/v5/launches").body()
}

suspend fun getTodos(spaceXApi: SpaceXApi): Todos{
    return spaceXApi.httpClient.get("https://jsonplaceholder.typicode.com/todos/1").body()
}


class SpaceXApi {
    val httpClient = HttpClient {
        install(Logging){
            logger = DebugKtorLogger()
            level = LogLevel.BODY
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }
}

class DebugKtorLogger: Logger {
    override fun log(message: String) {
        logger { "ktor:: $message" }
    }
}