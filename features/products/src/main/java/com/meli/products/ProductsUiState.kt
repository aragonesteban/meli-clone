package com.meli.products

import com.meli.domain.model.products.ProductItem

sealed interface ProductsUiState {
    object Loading : ProductsUiState
    data class ShowProductsList(val data: List<ProductItem>) : ProductsUiState
    object Error : ProductsUiState
}