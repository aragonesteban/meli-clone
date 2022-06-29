package com.meli.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.meli.data.local.MeliDatabase
import com.meli.data.local.dao.SearchHistoryDao
import com.meli.data.local.entities.SearchHistory
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MeliDatabaseTest {

    private lateinit var database: MeliDatabase
    private lateinit var searchHistoryDao: SearchHistoryDao

    private val mockSearchHistory = SearchHistory(query = "query")

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MeliDatabase::class.java).build()
        searchHistoryDao = database.searchHistoryDao()
    }

    @Test
    fun writeAndReadSearchHistory() = runBlocking {
        searchHistoryDao.saveSearchQuery(mockSearchHistory)
        val searchHistorySaved = listOf(mockSearchHistory)
        val searchHistory = searchHistoryDao.getSearchHistory()
        assertEquals(searchHistorySaved[0].query, searchHistory[0].query)
    }

    @Test
    fun writeAndDeleteAndReadSearchHistory() = runBlocking {
        searchHistoryDao.saveSearchQuery(mockSearchHistory)
        searchHistoryDao.deleteSearchHistory()
        val searchHistory = searchHistoryDao.getSearchHistory()
        assertEquals(0, searchHistory.size)
    }

}