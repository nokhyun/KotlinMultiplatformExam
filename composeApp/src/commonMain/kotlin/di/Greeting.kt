package di

class Greeting(
    private val platform: Platform
) {
    fun greeting(): String = "Hello, ${platform.name}"
}