package com.meli.domain.usecases.search

import com.meli.domain.MeliResult

interface SearchHistoryUseCase {
    suspend fun saveSearchQueryHistory(searchHistoryList: List<String>, query: String)
    suspend fun getSearchHistory(): MeliResult<List<String>>
}