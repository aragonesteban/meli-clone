package com.meli.products

import com.meli.domain.MeliResult
import com.meli.domain.model.products.ProductItem
import com.meli.domain.usecases.products.ProductsUseCase
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
class ProductsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val query = "query"
    private val mockProductsList = listOf(
        ProductItem(
            id = "test",
            title = "test",
            price = 0L,
            thumbnail = "test",
            duesQuantity = 1,
            duesAmount = 1.0,
        )
    )

    private val mockProductsUseCase = mock<ProductsUseCase> {
        onBlocking { getProductListByQuery(query) } doReturn MeliResult.Success(mockProductsList)
    }

    private lateinit var viewModel: ProductsViewModel

    @Before
    fun setUp() {
        viewModel = ProductsViewModel(mockProductsUseCase)
    }

    @Test
    fun `getProductListByQuery() verify that getProductListByQuery() is calling from ProductsUseCase`() {
        runTest {
            viewModel.getProductListByQuery(true, query)
            verify(mockProductsUseCase).getProductListByQuery(query)
        }
    }

    @Test
    fun `getProductListByQuery() when use case returns MeliResult Success then state emit ShowProductsList`() {
        runTest {
            viewModel.getProductListByQuery(true, query)
            assertTrue(viewModel.uiState.value is ProductsUiState.ShowProductsList)
        }
    }

    @Test
    fun `getProductListByQuery() when use case returns MeliResult Success then state emit ShowEmptyStateProducts`() {
        runTest {
            val mockProductsUseCase = mock<ProductsUseCase> {
                onBlocking { getProductListByQuery(query) } doReturn MeliResult.Success(listOf())
            }
            val viewModel = ProductsViewModel(mockProductsUseCase)
            viewModel.getProductListByQuery(true, query)
            assertTrue(viewModel.uiState.value is ProductsUiState.ShowEmptyStateProducts)
        }
    }

    @Test
    fun `getProductListByQuery() when there is not internet connection then state emit ErrorInternetConnection`() {
        runTest {
            viewModel.getProductListByQuery(false, query)
            assertTrue(viewModel.uiState.value is ProductsUiState.ErrorInternetConnection)
        }
    }

}