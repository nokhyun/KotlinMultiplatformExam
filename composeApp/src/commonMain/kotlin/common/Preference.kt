package common

import PlatformContext

expect class Preference(
    context: PlatformContext
){
    fun set(key: String, value: String)
    fun get(key: String): String

    fun allKey(): List<String>
    fun allValue(): List<String>
}