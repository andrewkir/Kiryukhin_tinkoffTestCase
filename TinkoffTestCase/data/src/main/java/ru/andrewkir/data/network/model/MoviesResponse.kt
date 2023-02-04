package ru.andrewkir.data.network.model

data class MoviesResponse(
    val filmId: Int?,
    val nameRu: String?,
    val posterUrl: String?,
    val genres: List<GenresResponse>?,
    val year: String?,
    val countries: List<CountriesResponse>?
)

data class MoviesDetailsResponse(
    val filmId: Int?,
    val nameRu: String?,
    val posterUrl: String?,
    val genres: List<GenresResponse>?,
    val year: String?,
    val countries: List<CountriesResponse>?,
    val description: String?
)

data class CountriesResponse(
    val country: String?
)