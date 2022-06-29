package com.meli.productdetail

import com.meli.domain.MeliResult
import com.meli.domain.model.products.ProductDetail
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
class ProductDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val productId = "productId"
    private val mockProductDetail = ProductDetail(
        id = productId,
        title = "test",
        price = 0L,
        soldQuantity = 1,
        picturesList = listOf(),
        attributes = listOf(),
        warranty = "test",
        availableQuantity = 1,
    )

    private val mockProductsUseCase = mock<ProductsUseCase> {
        onBlocking { getProductById(productId) } doReturn MeliResult.Success(mockProductDetail)
    }

    private lateinit var viewModel: ProductDetailViewModel

    @Before
    fun setUp() {
        viewModel = ProductDetailViewModel(mockProductsUseCase)
    }

    @Test
    fun `getProductDetailById() verify that getProductById() is calling from ProductsUseCase`() {
        runTest {
            viewModel.getProductDetailById(true, productId)
            verify(mockProductsUseCase).getProductById(productId)
        }
    }

    @Test
    fun `getProductListByQuery() when use case returns MeliResult Success then state emit ShowProductsList`() {
        runTest {
            viewModel.getProductDetailById(true, productId)
            assertTrue(viewModel.uiState.value is ProductDetailUiState.ShowProductDetail)
        }
    }

    @Test
    fun `getProductDetailById() when there is not internet connection then state emit ErrorInternetConnection`() {
        runTest {
            viewModel.getProductDetailById(false, productId)
            assertTrue(viewModel.uiState.value is ProductDetailUiState.ErrorInternetConnection)
        }
    }

}