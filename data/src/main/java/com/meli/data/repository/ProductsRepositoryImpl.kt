package com.meli.data.repository

import com.meli.data.remote.products.RemoteProducts
import com.meli.domain.MeliResult
import com.meli.domain.model.products.ProductDetail
import com.meli.domain.model.products.ProductItem
import com.meli.domain.repository.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteProducts: RemoteProducts
) : ProductsRepository {

    override suspend fun getProductListByQuery(query: String): MeliResult<List<ProductItem>> {
        return withContext(Dispatchers.IO) {
            remoteProducts.getProductListByQuery(query)
        }
    }

    override suspend fun getProductById(productId: String): MeliResult<ProductDetail> {
        return withContext(Dispatchers.IO) {
            remoteProducts.getProductById(productId)
        }
    }

}