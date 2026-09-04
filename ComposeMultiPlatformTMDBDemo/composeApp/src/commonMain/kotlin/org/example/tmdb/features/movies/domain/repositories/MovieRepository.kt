package org.example.tmdb.features.movies.domain.repositories

import org.example.tmdb.features.movies.domain.entities.Movie

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): Result<List<Movie>>
    suspend fun searchMovies(query: String, page: Int): Result<List<Movie>>
}
