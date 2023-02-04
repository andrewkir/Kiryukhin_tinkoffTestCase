package ru.andrewkir.tinkofftestcase.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.andrewkir.data.db.MoviesDataBase
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideYourDao(db: MoviesDataBase) = db.getMoviesDao()
}