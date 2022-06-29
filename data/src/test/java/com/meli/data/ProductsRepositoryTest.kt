package com.meli.data

import com.meli.data.remote.products.RemoteProducts
import com.meli.data.repository.ProductsRepositoryImpl
import com.meli.domain.MeliResult
import com.meli.domain.model.products.ProductDetail
import com.meli.domain.model.products.ProductItem
import com.meli.domain.repository.ProductsRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ProductsRepositoryTest {

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

    private val mockRemoteProducts = mock<RemoteProducts> {
        onBlocking { getProductListByQuery(query) } doReturn MeliResult.Success(mockProductsList)
        onBlocking { getProductById(productId) } doReturn MeliResult.Success(mockProductDetail)
    }

    private lateinit var respository: ProductsRepository

    @Before
    fun setUp() {
        respository = ProductsRepositoryImpl(mockRemoteProducts)
    }

    @Test
    fun `getProductListByQuery() verify that getProductListByQuery() is calling from RemoteProducts`() {
        runTest {
            respository.getProductListByQuery(query)
            verify(mockRemoteProducts).getProductListByQuery(query)
        }
    }

    @Test
    fun `getProductListByQuery() when remote returns MeliResult Success then repository returns Success`() {
        runTest {
            val result = respository.getProductListByQuery(query)
            assertTrue(result is MeliResult.Success)
        }
    }

    @Test
    fun `getProductById() verify that getProductById() is calling from RemoteProducts`() {
        runTest {
            respository.getProductById(productId)
            verify(mockRemoteProducts).getProductById(productId)
        }
    }

    @Test
    fun `getProductById() when repository returns MeliResult Success then repository returns Success`() {
        runTest {
            val result = respository.getProductById(productId)
            assertTrue(result is MeliResult.Success)
        }
    }


}