package common

import PlatformContext
import platform.Foundation.NSUserDefaults
import platform.Foundation.dictionaryWithValuesForKeys

actual class Preference actual constructor(context: PlatformContext) {

    private val standardUserDefaults = NSUserDefaults.standardUserDefaults

    actual fun set(key: String, value: String) {
        if (hasObject(key)) {
            standardUserDefaults.removeObjectForKey(key)
        } else {
            standardUserDefaults.setObject(value = value, forKey = key)
        }
    }

    actual fun get(key: String): String {
        return standardUserDefaults.stringForKey(key) ?: "empty"
    }

    private fun hasObject(key: String): Boolean {
        return standardUserDefaults.objectForKey(key) != null
    }

    actual fun allKey(): List<String> {
        return standardUserDefaults.dictionaryRepresentation().keys
            .filterNotNull()
            .filter { it.toString().startsWith("https://") }
            .map { it.toString() }
    }

    actual fun allValue(): List<String> {
        return standardUserDefaults.dictionaryWithValuesForKeys(allKey()).values
            .filterNotNull()
            .map { it.toString() }
    }
}