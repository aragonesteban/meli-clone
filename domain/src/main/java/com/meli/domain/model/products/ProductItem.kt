package com.meli.domain.model.products

data class ProductItem(
    val id: String,
    val title: String,
    val price: Long,
    val thumbnail: String,
    val duesQuantity: Int,
    val duesAmount: Double
)