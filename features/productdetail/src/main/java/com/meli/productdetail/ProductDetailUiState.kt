package com.meli.productdetail

import com.meli.domain.model.products.ProductDetail

sealed interface ProductDetailUiState {
    object Loading : ProductDetailUiState
    data class ShowProductDetail(val data: ProductDetail) : ProductDetailUiState
    object ErrorInternetConnection : ProductDetailUiState
    object Error : ProductDetailUiState
}