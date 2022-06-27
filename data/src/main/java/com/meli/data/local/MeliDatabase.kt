package com.meli.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meli.data.local.dao.SearchHistoryDao
import com.meli.data.local.entities.SearchHistory

@Database(entities = [SearchHistory::class], version = 1)
abstract class MeliDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}