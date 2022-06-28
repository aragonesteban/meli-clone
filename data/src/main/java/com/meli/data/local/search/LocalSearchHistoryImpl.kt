package com.meli.data.local.search

import com.meli.data.local.dao.SearchHistoryDao
import com.meli.data.local.entities.SearchHistory
import javax.inject.Inject

class LocalSearchHistoryImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) : LocalSearchHistory {

    override fun saveSearchQueryHistory(query: String) {
        searchHistoryDao.saveSearchQuery(
            SearchHistory(query = query)
        )
    }

    override fun getSearchHistory(): List<String> {
        return searchHistoryDao.getSearchHistory().map { item -> item.query }
    }

    override fun deleteSearchHistory() {
        searchHistoryDao.deleteSearchHistory()
    }

}