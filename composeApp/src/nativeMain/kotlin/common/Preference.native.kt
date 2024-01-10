package common

import PlatformContext

actual class Preference actual constructor(context: PlatformContext) {
    actual fun set(key: String, value: String) {
    }

    actual fun get(key: String): String {
        return "IOS"
    }
}