package common

import PlatformContext
import android.content.Context
import androidx.core.content.edit
import logger

actual class Preference actual constructor(private val context: PlatformContext) {

    private val sharedPreference = context.android.applicationContext.getSharedPreferences(
        "favoriteData",
        Context.MODE_PRIVATE
    )

    actual fun set(key: String, value: String) {
        sharedPreference.also {
            it.edit {
                if (!it.getString(key, null).isNullOrEmpty()) {
                    remove(key)
                } else {
                    putString(key, value)
                }
            }
        }
    }

    actual fun get(key: String): String {
        return sharedPreference.let {
            it.getString(key, "empty").toString()
        }
    }

    actual fun allKey(): List<String> {
        return sharedPreference.let {
            it.all
                .map { it.key }
                .toList()
        }
    }

    actual fun allValue(): List<String> {
        return sharedPreference.let {
            it.all
                .map { it.value.toString() }
                .toList()
        }
    }
}