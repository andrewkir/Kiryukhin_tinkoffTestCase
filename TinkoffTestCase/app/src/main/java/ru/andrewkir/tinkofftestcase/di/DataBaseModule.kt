package ru.andrewkir.tinkofftestcase.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.andrewkir.data.db.MoviesDataBase
import javax.inject.Singleton


@Module
class DataBaseModule(context: Context) {
    private val context: Context

    init {
        this.context = context
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(): MoviesDataBase {
        return Room.databaseBuilder(context, MoviesDataBase::class.java, "todo_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

}