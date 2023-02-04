package ru.andrewkir.data.network.model

import ru.andrewkir.domain.models.MovieModel

data class TopMoviesResponse(
    val pagesCount: Int,
    val films: List<MoviesResponse>
)