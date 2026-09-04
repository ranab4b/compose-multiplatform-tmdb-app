package org.example.tmdb.features.movies.data.datasources

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.example.tmdb.core.constants.ApiConstants
import org.example.tmdb.features.movies.data.models.GenreResponseDto
import org.example.tmdb.features.movies.data.models.MovieResponseDto

class MovieRemoteDataSource(private val client: HttpClient) {
    private var genreCache: Map<Int, String>? = null

    suspend fun getGenreMap(): Map<Int, String> {
        genreCache?.let { return it }
        return try {
            val response: GenreResponseDto = client.get("${ApiConstants.BASE_URL}/genre/movie/list") {
                parameter("api_key", ApiConstants.API_KEY)
            }.body()
            val map = response.genres.associate { it.id to it.name }
            genreCache = map
            map
        } catch (e: Exception) {
            emptyMap()
        }
    }

    suspend fun getPopularMovies(page: Int): MovieResponseDto {
        return client.get("${ApiConstants.BASE_URL}/movie/popular") {
            parameter("api_key", ApiConstants.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun searchMovies(query: String, page: Int): MovieResponseDto {
        return client.get("${ApiConstants.BASE_URL}/search/movie") {
            parameter("api_key", ApiConstants.API_KEY)
            parameter("query", query)
            parameter("page", page)
        }.body()
    }
}
