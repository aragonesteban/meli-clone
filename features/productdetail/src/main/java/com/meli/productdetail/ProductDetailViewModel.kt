package com.meli.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.domain.MeliResult
import com.meli.domain.usecases.products.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState

    fun getProductDetailById(productId: String) {
        viewModelScope.launch {
            _uiState.value = when (val result = productsUseCase.getProductById(productId)) {
                is MeliResult.Success -> ProductDetailUiState.ShowProductDetail(result.data)
                is MeliResult.Error -> ProductDetailUiState.Error
            }
        }
    }

}