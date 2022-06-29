package com.meli.domain

import com.meli.domain.repository.SearchHistoryRepository
import com.meli.domain.usecases.search.SearchHistoryUseCase
import com.meli.domain.usecases.search.SearchHistoryUseCaseImpl
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class SearchHistoryUseCaseTest {

    private val query = "query"
    private val mockSearchHistoryList = listOf(query)

    private val mockSearchHistoryRepository = mock<SearchHistoryRepository> {
        onBlocking { getSearchHistory() } doReturn MeliResult.Success(mockSearchHistoryList)
    }

    private lateinit var useCase: SearchHistoryUseCase

    @Before
    fun setUp() {
        useCase = SearchHistoryUseCaseImpl(mockSearchHistoryRepository)
    }

    @Test
    fun `getSearchHistory() verify that getSearchHistory() is calling from SearchHistoryRepository`() {
        runTest {
            useCase.getSearchHistory()
            verify(mockSearchHistoryRepository).getSearchHistory()
        }
    }

    @Test
    fun `getSearchHistory() when repository returns MeliResult Success then use case returns Success`() {
        runTest {
            val result = useCase.getSearchHistory()
            assertTrue(result is MeliResult.Success)
        }
    }

    @Test
    fun `saveSearchQueryHistory() verify that saveSearchQueryHistory() is calling from SearchHistoryRepository`() {
        runTest {
            useCase.saveSearchQueryHistory(mockSearchHistoryList, "query1")
            verify(mockSearchHistoryRepository).saveSearchQueryHistory("query1")
        }
    }

    @Test
    fun `deleteSearchHistory() verify that deleteSearchHistory() is calling from SearchHistoryRepository`() {
        runTest {
            useCase.deleteSearchHistory()
            verify(mockSearchHistoryRepository).deleteSearchHistory()
        }
    }

}