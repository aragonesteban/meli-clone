package com.meli.search

sealed interface SearchUiState {
    object Loading : SearchUiState
    data class ShowSearchHistory(val data: List<String>) : SearchUiState
    object Error : SearchUiState
}