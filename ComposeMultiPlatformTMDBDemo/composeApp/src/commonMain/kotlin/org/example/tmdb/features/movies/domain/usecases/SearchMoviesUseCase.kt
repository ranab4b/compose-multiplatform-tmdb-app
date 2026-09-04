package org.example.tmdb.features.movies.domain.usecases

import org.example.tmdb.features.movies.domain.entities.Movie
import org.example.tmdb.features.movies.domain.repositories.MovieRepository

class SearchMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(query: String, page: Int): Result<List<Movie>> {
        return repository.searchMovies(query, page)
    }
}
