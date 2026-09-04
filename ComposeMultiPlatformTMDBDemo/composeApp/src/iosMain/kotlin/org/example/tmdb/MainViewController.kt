package org.example.tmdb

import androidx.compose.ui.window.ComposeUIViewController
import org.example.tmdb.di.initKoin

private var isKoinStarted = false

fun MainViewController() = ComposeUIViewController {
    if (!isKoinStarted) {
        initKoin()
        isKoinStarted = true
    }
    App()
}
