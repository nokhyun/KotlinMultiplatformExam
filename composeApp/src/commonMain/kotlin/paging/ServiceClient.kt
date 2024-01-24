package paging

import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import network.DebugKtorLogger
import network.FakeService

/**
 * Add FakeApi Client
 * */
object ServiceClient {
    val ktorfitClient = ktorfit {
        baseUrl("https://api.slingacademy.com/")
        httpClient {
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
    }.create<FakeService>()
}