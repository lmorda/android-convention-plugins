package com.lmorda.data.di

import com.lmorda.data.DataRepositoryImpl
import com.lmorda.domain.DataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMyApplicationRepository(
        impl: DataRepositoryImpl
    ): DataRepository
}
