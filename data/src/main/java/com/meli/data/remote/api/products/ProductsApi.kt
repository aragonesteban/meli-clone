package com.meli.data.remote.api.products

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val SEARCH_URL = "sites/MCO/search"
private const val QUERY = "q"
private const val ITEMS_PREFIX = "items"
private const val PRODUCT_ID = "productId"

interface ProductsApi {

    @GET(SEARCH_URL)
    suspend fun getProductsListByQuery(
        @Query(QUERY) query: String
    ): Response<ProductsResultApi>

    @GET("$ITEMS_PREFIX/{$PRODUCT_ID}")
    suspend fun getProductById(
        @Path(PRODUCT_ID) productId: String
    ): Response<ProductDetailApi>

}