package com.meli.data.remote.api.products

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailApi(
    val id: String? = String(),
    val title: String? = String(),
    val price: Long? = null,
    @SerialName("sold_quantity")
    val soldQuantity: Int? = null,
    val pictures: List<ProductPictureApi>? = listOf(),
    val attributes: List<ProductAttributeApi>? = listOf(),
    val warranty: String? = String(),
    @SerialName("available_quantity")
    val availableQuantity: Int? = null
)

@Serializable
data class ProductPictureApi(
    val id: String? = String(),
    val url: String? = String()
)

@Serializable
data class ProductAttributeApi(
    val id: String? = String(),
    val name: String? = String(),
    @SerialName("value_name")
    var valueName: String? = String()
)