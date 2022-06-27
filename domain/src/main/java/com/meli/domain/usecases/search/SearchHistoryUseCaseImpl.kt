package com.meli.domain.usecases.search

import com.meli.domain.MeliResult
import com.meli.domain.repository.SearchHistoryRepository
import java.util.*
import javax.inject.Inject

class SearchHistoryUseCaseImpl @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) : SearchHistoryUseCase {

    override suspend fun saveSearchQueryHistory(searchHistoryList: List<String>, query: String) {
        val queryAlreadyExists = searchHistoryList.any { queryItem ->
            queryItem.lowercase().contains(query.lowercase())
        }
        if (queryAlreadyExists.not()) {
            searchHistoryRepository.saveSearchQueryHistory(query)
        }
    }

    override suspend fun getSearchHistory(): MeliResult<List<String>> {
        return searchHistoryRepository.getSearchHistory()
    }

}