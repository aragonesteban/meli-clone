package com.meli.domain.repository

import com.meli.domain.MeliResult
import com.meli.domain.model.products.ProductDetail
import com.meli.domain.model.products.ProductItem

interface ProductsRepository {
    suspend fun getProductListByQuery(query: String): MeliResult<List<ProductItem>>
    suspend fun getProductById(productId: String): MeliResult<ProductDetail>
}