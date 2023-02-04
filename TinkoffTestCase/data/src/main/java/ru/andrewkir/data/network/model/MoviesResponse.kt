package ru.andrewkir.data.network.model

data class MoviesResponse(
    val filmId: Int?,
    val nameRu: String?,
    val posterUrl: String?,
    val genres: List<GenresResponse>?
)