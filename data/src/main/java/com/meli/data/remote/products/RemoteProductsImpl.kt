package com.meli.data.remote.products

import com.meli.data.remote.api.products.ProductsApi
import com.meli.data.remote.api.products.executeRetrofitRequest
import com.meli.data.remote.api.products.handleResultRetrofit
import com.meli.domain.MeliResult
import com.meli.domain.model.products.ProductDetail
import com.meli.domain.model.products.ProductItem
import javax.inject.Inject

class RemoteProductsImpl @Inject constructor(
    private val productApi: ProductsApi
) : RemoteProducts {

    override suspend fun getProductListByQuery(query: String): MeliResult<List<ProductItem>> {
        val result = executeRetrofitRequest { productApi.getProductsListByQuery(query) }
        return handleResultRetrofit(result) {
            it.transformToProductsList()
        }
    }

    override suspend fun getProductById(productId: String): MeliResult<ProductDetail> {
        val result = executeRetrofitRequest { productApi.getProductById(productId) }
        return handleResultRetrofit(result) {
            it.transformToProductDetail()
        }
    }

}