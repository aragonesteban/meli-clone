package com.meli.domain.usecases.products

import com.meli.domain.MeliResult
import com.meli.domain.model.products.ProductDetail
import com.meli.domain.model.products.ProductItem
import com.meli.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : ProductsUseCase {

    override suspend fun getProductListByQuery(query: String): MeliResult<List<ProductItem>> {
        return productsRepository.getProductListByQuery(query)
    }

    override suspend fun getProductById(productId: String): MeliResult<ProductDetail> {
        return productsRepository.getProductById(productId)
    }

}