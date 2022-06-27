package com.meli.domain.repository

import com.meli.domain.MeliResult

interface SearchHistoryRepository {
    suspend fun saveSearchQueryHistory(query: String)
    suspend fun getSearchHistory(): MeliResult<List<String>>
}