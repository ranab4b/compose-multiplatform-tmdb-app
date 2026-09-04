package org.example.tmdb.features.movies.data.repositories

import org.example.tmdb.features.movies.data.datasources.MovieRemoteDataSource
import org.example.tmdb.features.movies.data.mappers.toDomain
import org.example.tmdb.features.movies.domain.entities.Movie
import org.example.tmdb.features.movies.domain.repositories.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return runCatching {
            val genreMap = remoteDataSource.getGenreMap()
            val dto = remoteDataSource.getPopularMovies(page)
            dto.results.map { it.toDomain(genreMap) }
        }
    }

    override suspend fun searchMovies(query: String, page: Int): Result<List<Movie>> {
        return runCatching {
            val genreMap = remoteDataSource.getGenreMap()
            val dto = remoteDataSource.searchMovies(query, page)
            dto.results.map { it.toDomain(genreMap) }
        }
    }
}
