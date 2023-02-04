package ru.andrewkir.domain.models

data class MovieModel(
    val id: Int? = null,
    val name: String? = null,
    val posterUrl: String? = null,
    val genres: List<String>? = null,
    val year: String? = null,
    val countries: List<String>? = null,
    val description: String? = null
)