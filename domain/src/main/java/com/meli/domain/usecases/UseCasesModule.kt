package com.meli.domain.usecases

import com.meli.domain.usecases.products.ProductsUseCase
import com.meli.domain.usecases.products.ProductsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {

    @Binds
    @Singleton
    abstract fun bindProductsUseCase(
        productsUseCaseImpl: ProductsUseCaseImpl
    ): ProductsUseCase

}