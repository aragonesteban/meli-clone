package com.meli.data.repository

import com.meli.data.local.search.LocalSearchHistory
import com.meli.domain.MeliResult
import com.meli.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val localSearchHistory: LocalSearchHistory
) : SearchHistoryRepository {

    override suspend fun saveSearchQueryHistory(query: String) {
        withContext(Dispatchers.IO) {
            localSearchHistory.saveSearchQueryHistory(query)
        }
    }

    override suspend fun getSearchHistory(): MeliResult<List<String>> {
        return withContext(Dispatchers.IO) {
            try {
                MeliResult.Success(localSearchHistory.getSearchHistory())
            } catch (e: Exception) {
                MeliResult.Error()
            }
        }
    }

}