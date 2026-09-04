package org.example.tmdb.di

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.example.tmdb.features.movies.data.datasources.MovieRemoteDataSource
import org.example.tmdb.features.movies.data.repositories.MovieRepositoryImpl
import org.example.tmdb.features.movies.domain.repositories.MovieRepository
import org.example.tmdb.features.movies.domain.usecases.GetPopularMoviesUseCase
import org.example.tmdb.features.movies.domain.usecases.SearchMoviesUseCase
import org.example.tmdb.features.movies.presentation.viewmodel.MovieViewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single {
        val json = get<Json>()
        HttpClient {
            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    single { MovieRemoteDataSource(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }

    factory { GetPopularMoviesUseCase(get()) }
    factory { SearchMoviesUseCase(get()) }

    factory { MovieViewModel(get(), get()) }
}
