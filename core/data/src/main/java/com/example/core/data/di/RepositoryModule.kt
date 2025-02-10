package com.example.core.data.di

import com.example.core.data.repository.CinemaniaRepositoryImpl
import com.example.core.domain.repository.CinemaniaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    internal abstract fun bindsCinemaniaRepository(
        cinemaniaRepository: CinemaniaRepositoryImpl,
    ): CinemaniaRepository
}