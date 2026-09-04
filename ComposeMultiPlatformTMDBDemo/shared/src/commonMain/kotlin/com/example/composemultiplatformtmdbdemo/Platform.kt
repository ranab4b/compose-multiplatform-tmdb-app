package com.example.composemultiplatformtmdbdemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform