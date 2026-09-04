package org.example.tmdb.presentation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.example.tmdb.features.movies.domain.entities.Movie
import org.example.tmdb.features.movies.domain.repositories.MovieRepository
import org.example.tmdb.features.movies.domain.usecases.GetPopularMoviesUseCase
import org.example.tmdb.features.movies.domain.usecases.SearchMoviesUseCase
import org.example.tmdb.features.movies.presentation.viewmodel.MovieState
import org.example.tmdb.features.movies.presentation.viewmodel.MovieViewModel

class FakeMovieRepository(private val movies: List<Movie>) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> = Result.success(movies)
    override suspend fun searchMovies(query: String, page: Int): Result<List<Movie>> = Result.success(movies)
}

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun initialState_shouldEmit_LoadingInitial_with_customMessage() = runTest {
        Dispatchers.setMain(testDispatcher)

        val fakeMovie = Movie(1, "Inception", "Overview", "", "2010", 8.8, listOf("Sci-Fi"))
        val repo = FakeMovieRepository(listOf(fakeMovie))
        val viewModel = MovieViewModel(GetPopularMoviesUseCase(repo), SearchMoviesUseCase(repo))

        val state = viewModel.uiState.value
        assert(state is MovieState.LoadingInitial)
        assertEquals(
            "Preparing the experience for you, hold on with us...",
            (state as MovieState.LoadingInitial).message
        )
    }
}
