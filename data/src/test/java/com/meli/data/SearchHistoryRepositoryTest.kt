package com.meli.data

import com.meli.data.local.search.LocalSearchHistory
import com.meli.data.repository.SearchHistoryRepositoryImpl
import com.meli.domain.MeliResult
import com.meli.domain.repository.SearchHistoryRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class SearchHistoryRepositoryTest {

    private val query = "query"
    private val mockSearchHistoryList = listOf(query)

    private val mockLocalSearchHistory = mock<LocalSearchHistory> {
        onBlocking { getSearchHistory() } doReturn mockSearchHistoryList
    }

    private lateinit var repository: SearchHistoryRepository

    @Before
    fun setUp() {
        repository = SearchHistoryRepositoryImpl(mockLocalSearchHistory)
    }

    @Test
    fun `getSearchHistory() verify that getSearchHistory() is calling from LocalSearchHistory`() {
        runTest {
            repository.getSearchHistory()
            verify(mockLocalSearchHistory).getSearchHistory()
        }
    }

    @Test
    fun `getSearchHistory() when local returns MeliResult Success then repository returns Success`() {
        runTest {
            val result = repository.getSearchHistory()
            assertTrue(result is MeliResult.Success)
        }
    }

    @Test
    fun `saveSearchQueryHistory() verify that saveSearchQueryHistory() is calling from LocalSearchHistory`() {
        runTest {
            repository.saveSearchQueryHistory(query)
            verify(mockLocalSearchHistory).saveSearchQueryHistory(query)
        }
    }

    @Test
    fun `deleteSearchHistory() verify that deleteSearchHistory() is calling from LocalSearchHistory`() {
        runTest {
            repository.deleteSearchHistory()
            verify(mockLocalSearchHistory).deleteSearchHistory()
        }
    }

}