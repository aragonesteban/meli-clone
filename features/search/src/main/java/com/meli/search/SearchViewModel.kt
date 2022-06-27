package com.meli.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.domain.MeliResult
import com.meli.domain.usecases.search.SearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHistoryUseCase: SearchHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState: StateFlow<SearchUiState> = _uiState

    private var _searchHistoryList = listOf<String>()

    fun getSearchHistory() {
        viewModelScope.launch {
            _uiState.value = when(val result = searchHistoryUseCase.getSearchHistory()) {
                is MeliResult.Success -> {
                    _searchHistoryList = result.data
                    SearchUiState.ShowSearchHistory(result.data)
                }
                is MeliResult.Error -> SearchUiState.Error
            }
        }
    }

    fun saveSearchQuery(query: String) {
        viewModelScope.launch {
            searchHistoryUseCase.saveSearchQueryHistory(_searchHistoryList, query)
        }
    }

}