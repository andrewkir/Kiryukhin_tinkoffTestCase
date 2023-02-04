package ru.andrewkir.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.andrewkir.data.network.model.MoviesDetailsResponse
import ru.andrewkir.data.network.model.MoviesResponse
import ru.andrewkir.data.network.model.TopMoviesResponse

interface KinopoiskApi {

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b", "'Content-Type': 'application/json'")
    @GET("api/v2.2/films/top?type=TOP_250_BEST_FILMS")
    suspend fun getTopMovies(@Query("page") page: Int): TopMoviesResponse

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b", "'Content-Type': 'application/json'")
    @GET("api/v2.2/films/{filmID}")
    suspend fun getMovieInfo(@Path("filmID") filmId: Int): MoviesDetailsResponse
}