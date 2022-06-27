package com.meli.data.local.search

interface LocalSearchHistory {
    fun saveSearchQueryHistory(query: String)
    fun getSearchHistory(): List<String>
}