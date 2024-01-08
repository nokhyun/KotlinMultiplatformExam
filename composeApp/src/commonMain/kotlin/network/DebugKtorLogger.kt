package network

import io.ktor.client.plugins.logging.Logger
import logger

class DebugKtorLogger : Logger {
    override fun log(message: String) {
        logger { "ktor:: $message" }
    }
}