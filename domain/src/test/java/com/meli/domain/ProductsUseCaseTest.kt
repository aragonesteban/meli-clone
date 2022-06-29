package com.meli.domain

import com.meli.domain.model.products.ProductDetail
import com.meli.domain.model.products.ProductItem
import com.meli.domain.repository.ProductsRepository
import com.meli.domain.usecases.products.ProductsUseCase
import com.meli.domain.usecases.products.ProductsUseCaseImpl
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ProductsUseCaseTest {

    private val query = "query"
    private val productId = "productId"
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

    private val mockProductsRepository = mock<ProductsRepository> {
        onBlocking { getProductListByQuery(query) } doReturn MeliResult.Success(mockProductsList)
        onBlocking { getProductById(productId) } doReturn MeliResult.Success(mockProductDetail)
    }

    private lateinit var useCase: ProductsUseCase

    @Before
    fun setUp() {
        useCase = ProductsUseCaseImpl(mockProductsRepository)
    }

    @Test
    fun `getProductListByQuery() verify that getProductListByQuery() is calling from ProductsRepository`() {
        runTest {
            useCase.getProductListByQuery(query)
            verify(mockProductsRepository).getProductListByQuery(query)
        }
    }

    @Test
    fun `getProductListByQuery() when repository returns MeliResult Success then use case returns Success`() {
        runTest {
            val result = useCase.getProductListByQuery(query)
            assertTrue(result is MeliResult.Success)
        }
    }

    @Test
    fun `getProductById() verify that getProductById() is calling from ProductsRepository`() {
        runTest {
            useCase.getProductById(productId)
            verify(mockProductsRepository).getProductById(productId)
        }
    }

    @Test
    fun `getProductById() when repository returns MeliResult Success then use case returns Success`() {
        runTest {
            val result = useCase.getProductById(productId)
            assertTrue(result is MeliResult.Success)
        }
    }

}