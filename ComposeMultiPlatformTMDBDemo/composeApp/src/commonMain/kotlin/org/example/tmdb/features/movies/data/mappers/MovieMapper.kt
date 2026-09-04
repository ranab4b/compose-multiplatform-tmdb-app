package org.example.tmdb.features.movies.data.mappers

import org.example.tmdb.features.movies.data.models.MovieDto
import org.example.tmdb.features.movies.domain.entities.Movie

fun MovieDto.toDomain(genreMap: Map<Int, String>): Movie {
    val mappedGenres = this.genreIds?.mapNotNull { genreMap[it] } ?: emptyList()
    return Movie(
        id = this.id,
        title = this.title ?: "No Title",
        overview = this.overview ?: "No Overview Available.",
        posterPath = this.posterPath ?: "",
        releaseDate = this.releaseDate ?: "N/A",
        voteAverage = this.voteAverage ?: 0.0,
        genres = mappedGenres
    )
}
