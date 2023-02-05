package ru.andrewkir.data.entities

import androidx.room.*
import ru.andrewkir.domain.models.MovieModel

@Dao
interface MovieModelDao {
    @Query("SELECT * FROM MovieEntity")
    fun getMoviesList(): List<MovieEntity>

    @Insert
    fun addItem(item: MovieEntity)


    @Delete
    fun removeItem(item: MovieEntity)

    @Update
    fun updateItem(item: MovieEntity)
}