package ru.andrewkir.data.repositories

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
    private val kinopoiskApi: KinopoiskApi
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
                    pagingData.map {
                        MovieModel(
                            it.filmId,
                            it.nameRu,
                            it.posterUrl,
                            it.genres?.get(0)?.genre ?: ""
                        )
                    }
                }
            }
            is ApiResponse.OnErrorResponse -> {
                null
            }
        }
    }

    override fun addItem(item: MovieModel) {
        movieModelDao.addItem(
            MovieEntity(
                item.id ?: 0,
                item.name ?: "",
                item.posterUrl ?: ""
            )
        )
    }

    override fun removeItem(item: MovieModel) {
        movieModelDao.removeItem(
            MovieEntity(
                item.id ?: 0,
                item.name ?: "",
                item.posterUrl ?: ""
            )
        )
    }

    override fun updateItem(item: MovieModel) {
        movieModelDao.updateItem(
            MovieEntity(
                item.id ?: 0,
                item.name ?: "",
                item.posterUrl ?: ""
            )
        )
    }

    suspend fun <T> protectedApiCall(
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