package com.meli.search

import com.meli.domain.MeliResult
import com.meli.domain.usecases.search.SearchHistoryUseCase
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val query = "query"
    private val mockSearchHistoryList = listOf(query)

    private val mockSearchHistoryUseCase = mock<SearchHistoryUseCase> {
        onBlocking { getSearchHistory() } doReturn MeliResult.Success(mockSearchHistoryList)
    }

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        viewModel = SearchViewModel(mockSearchHistoryUseCase)
    }

    @Test
    fun `getSearchHistory() when use case returns MeliResult Success then state emit ShowSearchHistory`() {
        runTest {
            viewModel.getSearchHistory()
            assertTrue(viewModel.uiState.value is SearchUiState.ShowSearchHistory)
        }
    }

    @Test
    fun `saveSearchQuery() verify that saveSearchQuery() is calling from SearchHistoryUseCase`() {
        runTest {
            viewModel.getSearchHistory()
            viewModel.saveSearchQuery(query)
            verify(mockSearchHistoryUseCase).saveSearchQueryHistory(mockSearchHistoryList, query)
        }
    }

    @Test
    fun `deleteSearchHistory() verify that deleteSearchHistory() is calling from SearchHistoryUseCase`() {
        runTest {
            viewModel.deleteSearchHistory()
            verify(mockSearchHistoryUseCase).deleteSearchHistory()
        }
    }

}