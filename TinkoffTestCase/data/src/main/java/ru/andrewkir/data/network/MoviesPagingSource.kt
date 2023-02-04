package ru.andrewkir.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.andrewkir.data.network.model.MoviesResponse
import ru.andrewkir.data.network.model.TopMoviesResponse
import java.io.IOException

class MoviesPagingSource(
    private val api: KinopoiskApi
) : PagingSource<Int, MoviesResponse>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesResponse> {
        val pageIndex = params.key ?: 1
        return try {
            val response = api.getTopMovies(
                page = pageIndex
            )
            val movies = response.films
            val nextKey =
                if (movies.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / 20)
                }
            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MoviesResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}