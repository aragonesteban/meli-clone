package com.meli.data.remote.api.products

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResultApi(
    val results: List<ProductItemApi>? = listOf()
)

@Serializable
data class ProductItemApi(
    val id: String? = String(),
    val title: String? = String(),
    val price: Long? = null,
    val thumbnail: String? = String(),
    val installments: ProductInstallmentsApi? = null
)

@Serializable
data class ProductInstallmentsApi(
    val quantity: Int? = null,
    val amount: Double? = null
)