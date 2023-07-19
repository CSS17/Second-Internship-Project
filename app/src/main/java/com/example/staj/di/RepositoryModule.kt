package com.example.staj.di

import com.example.staj.service.SportsBookService
import com.example.staj.repository.SportsBookRepository
import com.example.staj.repository.SportsBookRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideSportsBookRepository(service: SportsBookService): SportsBookRepository {
        return SportsBookRepositoryImpl(service)
    }

}