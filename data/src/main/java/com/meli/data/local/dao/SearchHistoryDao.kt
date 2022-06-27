package com.meli.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.meli.data.local.entities.SearchHistory

@Dao
interface SearchHistoryDao {

    @Insert
    fun saveSearchQuery(searchHistory: SearchHistory)

    @Query("SELECT * FROM search_history")
    fun getSearchHistory(): List<SearchHistory>

}