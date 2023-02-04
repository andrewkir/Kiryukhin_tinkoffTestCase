package ru.andrewkir.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.andrewkir.data.entities.GenresConverter
import ru.andrewkir.data.entities.MovieEntity
import ru.andrewkir.data.entities.MovieModelDao

@Database(
    entities = [MovieEntity::class],
    version = 5
)

@TypeConverters(GenresConverter::class)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun getMoviesDao(): MovieModelDao
}