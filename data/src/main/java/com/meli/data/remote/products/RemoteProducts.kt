package com.meli.data.remote.products

import com.meli.domain.MeliResult
import com.meli.domain.model.products.ProductDetail
import com.meli.domain.model.products.ProductItem

interface RemoteProducts {
    suspend fun getProductListByQuery(query: String): MeliResult<List<ProductItem>>
    suspend fun getProductById(productId: String): MeliResult<ProductDetail>
}