package com.meli.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.domain.MeliResult
import com.meli.domain.model.products.ProductItem
import com.meli.domain.usecases.products.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductsUiState>(ProductsUiState.ShowInitialEmptyState)
    val uiState: StateFlow<ProductsUiState> = _uiState

    private var _productsList = listOf<ProductItem>()

    fun getProductListByQuery(query: String) {
        _uiState.value = ProductsUiState.Loading
        viewModelScope.launch {
            _uiState.value = when (val result = productsUseCase.getProductListByQuery(query)) {
                is MeliResult.Success -> {
                    if (result.data.isNotEmpty()) {
                        _productsList = result.data
                        ProductsUiState.ShowProductsList(result.data)
                    } else {
                        ProductsUiState.ShowEmptyStateProducts
                    }
                }
                is MeliResult.Error -> ProductsUiState.Error
            }
        }
    }

}