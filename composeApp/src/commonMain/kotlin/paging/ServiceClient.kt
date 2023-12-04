package paging

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import network_exam.DebugKtorLogger

/**
 * Add FakeApi Client
 * */
object ServiceClient {
    val fakePagingHttpClient = HttpClient {
        install(Logging) {
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