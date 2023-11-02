package com.nokhyun.kmmexam

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform