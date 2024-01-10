package common

import PlatformContext
import android.content.Context
import androidx.core.content.edit

actual class Preference actual constructor(private val context: PlatformContext) {
    actual fun set(key: String, value: String) {
        (context.android.applicationContext).getSharedPreferences("favoriteData", Context.MODE_PRIVATE).also {
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
        return (context.android.applicationContext).getSharedPreferences("favoriteData", Context.MODE_PRIVATE).let {
            it.getString(key, "empty").toString()
        }
    }
}