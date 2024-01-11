package common

import PlatformContext
import platform.Foundation.NSUserDefaults

actual class Preference actual constructor(context: PlatformContext) {
    actual fun set(key: String, value: String) {
        if (hasObject(key)) {
            NSUserDefaults.standardUserDefaults.removeObjectForKey(key)
        } else {
            NSUserDefaults.standardUserDefaults.setObject(value = value, forKey = key)
        }
    }

    actual fun get(key: String): String {
        return NSUserDefaults.standardUserDefaults.objectForKey(key).toString()
    }

    private fun hasObject(key: String): Boolean {
        return NSUserDefaults.standardUserDefaults.objectForKey(key) != null
    }
}