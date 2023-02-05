package ru.andrewkir.data.repositories

import android.content.Context
import android.widget.Toast
import androidx.paging.*
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.andrewkir.data.entities.MovieEntity
import ru.andrewkir.data.entities.MovieModelDao
import ru.andrewkir.data.network.KinopoiskApi
import ru.andrewkir.data.network.MoviesPagingSource
import ru.andrewkir.data.network.model.ApiResponse
import ru.andrewkir.data.network.model.MoviesResponse
import ru.andrewkir.domain.models.MovieModel
import ru.andrewkir.domain.repositories.MainRepository
import java.net.SocketTimeoutException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val movieModelDao: MovieModelDao,
    private val kinopoiskApi: KinopoiskApi,
    private val context: Context
) :
    MainRepository {

    interface MoviesRemoteDataSource {

        fun getMovies(): Flow<PagingData<MoviesResponse>>
    }

    internal class MoviesRemoteDataSourceImpl(
        private val api: KinopoiskApi
    ) : MoviesRemoteDataSource {

        override fun getMovies(): Flow<PagingData<MoviesResponse>> {
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    MoviesPagingSource(api = api)
                }
            ).flow
        }
    }

    override fun getMoviesList(): Flow<PagingData<MovieModel>>? {
        val result =
            runBlocking { protectedApiCall { MoviesRemoteDataSourceImpl(api = kinopoiskApi).getMovies() } }
        return when (result) {
            is ApiResponse.OnSuccessResponse -> {
                result.value.map { pagingData ->
                    pagingData.map { it ->
                        MovieModel(
                            id = it.filmId,
                            name = it.nameRu,
                            posterUrl = it.posterUrl,
                            genres = it.genres?.map { genre -> genre.genre ?: "" } ?: emptyList(),
                            year = it.year,
                            countries = it.countries?.map { country -> country.country ?: "" }
                                ?: emptyList()
                        )
                    }
                }
            }
            is ApiResponse.OnErrorResponse -> {
                Toast.makeText(context, "Произошла ошибка", Toast.LENGTH_SHORT).show()
                null
            }
        }
    }

    override fun getMovie(id: Int): MovieModel? {
        val result = runBlocking {
            protectedApiCall {
                kinopoiskApi.getMovieInfo(id)
            }
        }
        return when (result) {
            is ApiResponse.OnSuccessResponse -> {
                MovieModel(
                    id = result.value.filmId,
                    name = result.value.nameRu,
                    posterUrl = result.value.posterUrl,
                    genres = result.value.genres?.map { genre -> genre.genre ?: "" } ?: emptyList(),
                    year = result.value.year,
                    countries = result.value.countries?.map { country -> country.country ?: "" }
                        ?: emptyList(),
                    description = result.value.description
                )
            }
            else -> null
        }
    }

    override fun getFavouritesMovies(): List<MovieModel> {
        return movieModelDao.getMoviesList().map {
            MovieModel(
                id = it.filmId,
                name = it.name,
                description = it.description,
                genres = it.genres,
                countries = it.countries,
                year = it.year,
                posterUrl = it.posterUrl
            )
        }
    }

    override fun addItem(item: MovieModel) {
        movieModelDao.addItem(
            MovieEntity(
                item.id ?: 0,
                item.name ?: "",
                item.posterUrl ?: "",
                item.genres ?: emptyList(),
                item.countries ?: emptyList(),
                item.description ?: "",
                item.year ?: ""
            )
        )
    }

    override fun removeItem(item: MovieModel) {
        movieModelDao.removeItem(
            MovieEntity(
                item.id ?: 0,
                item.name ?: "",
                item.posterUrl ?: "",
                item.genres ?: emptyList(),
                item.countries ?: emptyList(),
                item.description ?: "",
                item.year ?: ""
            )
        )
    }

    override fun updateItem(item: MovieModel) {
        movieModelDao.updateItem(
            MovieEntity(
                item.id ?: 0,
                item.name ?: "",
                item.posterUrl ?: "",
                item.genres ?: emptyList(),
                item.countries ?: emptyList(),
                item.description ?: "",
                item.year ?: ""
            )
        )
    }

    private suspend fun <T> protectedApiCall(
        api: suspend () -> T
    ): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResponse.OnSuccessResponse(api.invoke())
            } catch (ex: Throwable) {
                when (ex) {
                    is HttpException -> {
                        ApiResponse.OnErrorResponse(false, ex.code(), ex.response()?.errorBody())
                    }
                    is SocketTimeoutException -> {
                        ApiResponse.OnErrorResponse(false, null, null)
                    }
                    is JsonSyntaxException -> {
                        ApiResponse.OnErrorResponse(false, null, null)
                    }
                    else -> {
                        ApiResponse.OnErrorResponse(true, null, null)
                    }
                }
            }
        }
    }
}