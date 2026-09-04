package org.example.tmdb.features.movies.domain.usecases

import org.example.tmdb.features.movies.domain.entities.Movie
import org.example.tmdb.features.movies.domain.repositories.MovieRepository

class GetPopularMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(page: Int): Result<List<Movie>> {
        return repository.getPopularMovies(page)
    }
}
