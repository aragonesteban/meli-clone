package com.meli.data.local

import android.app.Application
import androidx.room.Room
import com.meli.data.local.dao.SearchHistoryDao
import com.meli.data.local.search.LocalSearchHistory
import com.meli.data.local.search.LocalSearchHistoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val NAME_DATABASE = "MeliDatabase"

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideMeliDatabase(app: Application): MeliDatabase {
        return Room.databaseBuilder(
            app,
            MeliDatabase::class.java,
            NAME_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideSearchHistoryDao(meliDatabase: MeliDatabase): SearchHistoryDao {
        return meliDatabase.searchHistoryDao()
    }

    @Provides
    @Singleton
    fun provideLocalSearchHistory(searchHistoryDao: SearchHistoryDao): LocalSearchHistory {
        return LocalSearchHistoryImpl(searchHistoryDao)
    }

}