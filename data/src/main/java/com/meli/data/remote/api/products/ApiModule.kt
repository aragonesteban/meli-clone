package com.meli.data.remote.api.products

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit
import javax.inject.Singleton

private const val MELI_API = "https://api.mercadolibre.com/"

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun provideRetrofit(): Retrofit = buildRetrofit(MELI_API)

    @Provides
    @Singleton
    fun provideProductsApi(retrofit: Retrofit): ProductsApi =
        retrofit.create(ProductsApi::class.java)

}