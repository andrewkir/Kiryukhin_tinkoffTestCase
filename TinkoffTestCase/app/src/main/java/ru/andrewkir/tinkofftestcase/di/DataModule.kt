package ru.andrewkir.tinkofftestcase.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.andrewkir.data.repositories.MainRepositoryImpl
import ru.andrewkir.domain.repositories.MainRepository

@Module
abstract class DataModule {
    @Binds
    abstract fun provideMainRepository(mainRepository: MainRepositoryImpl): MainRepository
}