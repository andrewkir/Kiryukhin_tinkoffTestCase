package ru.andrewkir.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.andrewkir.domain.models.MovieModel

interface MainRepository {
    fun getMoviesList(): Flow<PagingData<MovieModel>>?
    fun getFavouritesMovies(): List<MovieModel>
    fun getMovie(id: Int): MovieModel?
    fun addItem(item: MovieModel)
    fun removeItem(item: MovieModel)
    fun updateItem(item: MovieModel)
}