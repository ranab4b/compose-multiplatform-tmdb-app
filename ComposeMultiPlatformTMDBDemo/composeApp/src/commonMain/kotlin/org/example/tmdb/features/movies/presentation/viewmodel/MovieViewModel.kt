package org.example.tmdb.features.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.tmdb.features.movies.domain.entities.Movie
import org.example.tmdb.features.movies.domain.usecases.GetPopularMoviesUseCase
import org.example.tmdb.features.movies.domain.usecases.SearchMoviesUseCase

sealed interface MovieState {
    data class LoadingInitial(val message: String) : MovieState
    data class Loaded(
        val movies: List<Movie>,
        val hasReachedMax: Boolean = false,
        val isLoadingMore: Boolean = false
    ) : MovieState
    data class Error(val message: String) : MovieState
}

class MovieViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieState>(
        MovieState.LoadingInitial("Preparing the experience for you, hold on with us...")
    )
    val uiState: StateFlow<MovieState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var currentQuery = ""
    private var searchJob: Job? = null

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            currentPage = 1
            currentQuery = ""
            _uiState.value = MovieState.LoadingInitial("Preparing the experience for you, hold on with us...")
            
            getPopularMoviesUseCase(currentPage)
                .onSuccess { movies ->
                    _uiState.value = MovieState.Loaded(movies = movies, hasReachedMax = movies.isEmpty())
                }
                .onFailure {
                    _uiState.value = MovieState.Error("Failed to fetch movies. Check network connection.")
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchJob?.cancel()
        currentQuery = query.trim()
        searchJob = viewModelScope.launch {
            delay(500)
            if (currentQuery.isEmpty()) {
                fetchPopularMovies()
                return@launch
            }
            currentPage = 1
            _uiState.value = MovieState.LoadingInitial("Searching movies...")
            
            searchMoviesUseCase(currentQuery, currentPage)
                .onSuccess { movies ->
                    _uiState.value = MovieState.Loaded(movies = movies, hasReachedMax = movies.isEmpty())
                }
                .onFailure {
                    _uiState.value = MovieState.Error("Error while searching movies.")
                }
        }
    }

    fun loadMore() {
        val currentState = _uiState.value
        if (currentState is MovieState.Loaded && !currentState.hasReachedMax && !currentState.isLoadingMore) {
            viewModelScope.launch {
                _uiState.value = currentState.copy(isLoadingMore = true)
                currentPage++
                
                val result = if (currentQuery.isEmpty()) {
                    getPopularMoviesUseCase(currentPage)
                } else {
                    searchMoviesUseCase(currentQuery, currentPage)
                }

                result.onSuccess { newMovies ->
                    if (newMovies.isEmpty()) {
                        _uiState.value = currentState.copy(hasReachedMax = true, isLoadingMore = false)
                    } else {
                        _uiState.value = MovieState.Loaded(
                            movies = currentState.movies + newMovies,
                            hasReachedMax = false,
                            isLoadingMore = false
                        )
                    }
                }.onFailure {
                    _uiState.value = currentState.copy(isLoadingMore = false)
                }
            }
        }
    }
}
