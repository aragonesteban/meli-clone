package com.meli.data.remote.products

import com.meli.data.remote.api.products.ProductDetailApi
import com.meli.data.remote.api.products.ProductsResultApi
import com.meli.domain.DEFAULT_DOUBLE
import com.meli.domain.DEFAULT_INT
import com.meli.domain.DEFAULT_LONG
import com.meli.domain.model.products.ProductAttribute
import com.meli.domain.model.products.ProductDetail
import com.meli.domain.model.products.ProductItem

fun ProductsResultApi.transformToProductsList(): List<ProductItem> {
    return results?.map { product ->
        ProductItem(
            id = product.id ?: String(),
            title = product.title ?: String(),
            price = product.price ?: DEFAULT_LONG,
            thumbnail = product.thumbnail ?: String(),
            duesQuantity = product.installments?.quantity ?: DEFAULT_INT,
            duesAmount = product.installments?.amount ?: DEFAULT_DOUBLE,
        )
    } ?: listOf()
}

fun ProductDetailApi.transformToProductDetail(): ProductDetail {
    return ProductDetail(
        id = id ?: String(),
        title = title ?: String(),
        price = price ?: DEFAULT_LONG,
        soldQuantity = soldQuantity ?: DEFAULT_INT,
        picturesList = pictures?.map { picture -> picture.url ?: String() } ?: listOf(),
        attributes = attributes?.map { attribute ->
            ProductAttribute(
                name = attribute.name ?: String(),
                value = attribute.valueName ?: String()
            )
        } ?: listOf(),
        warranty = warranty ?: String(),
        availableQuantity = availableQuantity ?: DEFAULT_INT,
    )
}