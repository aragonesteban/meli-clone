package com.meli.data.remote

import com.meli.data.remote.products.RemoteProducts
import com.meli.data.remote.products.RemoteProductsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun bindRemoteProducts(
        remoteProductsImpl: RemoteProductsImpl
    ): RemoteProducts

}