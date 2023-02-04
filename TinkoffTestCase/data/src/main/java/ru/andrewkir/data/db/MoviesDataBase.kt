package ru.andrewkir.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.andrewkir.data.entities.MovieEntity
import ru.andrewkir.data.entities.MovieModelDao

@Database(
    entities = [MovieEntity::class],
    version = 2
)

abstract class MoviesDataBase : RoomDatabase() {

    abstract fun getMoviesDao(): MovieModelDao
}