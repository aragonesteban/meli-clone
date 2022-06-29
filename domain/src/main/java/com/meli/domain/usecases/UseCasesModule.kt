package com.meli.domain.usecases

import com.meli.domain.usecases.products.ProductsUseCase
import com.meli.domain.usecases.products.ProductsUseCaseImpl
import com.meli.domain.usecases.search.SearchHistoryUseCase
import com.meli.domain.usecases.search.SearchHistoryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCasesModule {

    @Binds
    @ViewModelScoped
    abstract fun bindProductsUseCase(
        productsUseCaseImpl: ProductsUseCaseImpl
    ): ProductsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSearchHistoryUseCase(
        searchHistoryUseCaseImpl: SearchHistoryUseCaseImpl
    ): SearchHistoryUseCase

}