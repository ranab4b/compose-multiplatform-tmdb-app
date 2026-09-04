package org.example.tmdb

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.example.tmdb.di.appModule
import org.example.tmdb.features.movies.domain.entities.Movie
import org.example.tmdb.features.movies.presentation.screens.MovieDetailScreen
import org.example.tmdb.features.movies.presentation.screens.MovieListScreen
import org.example.tmdb.features.movies.presentation.viewmodel.MovieViewModel
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    KoinContext {
        MaterialTheme {
            var selectedMovie by remember { mutableStateOf<Movie?>(null) }
            val viewModel: MovieViewModel = koinViewModel()

            if (selectedMovie == null) {
                MovieListScreen(
                    viewModel = viewModel,
                    onMovieClick = { selectedMovie = it }
                )
            } else {
                MovieDetailScreen(
                    movie = selectedMovie!!,
                    onBackClick = { selectedMovie = null }
                )
            }
        }
    }
}
