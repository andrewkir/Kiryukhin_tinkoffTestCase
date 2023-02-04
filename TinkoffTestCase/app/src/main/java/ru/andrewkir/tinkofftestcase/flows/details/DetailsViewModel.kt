package ru.andrewkir.tinkofftestcase.flows.details

import ru.andrewkir.domain.models.MovieModel
import ru.andrewkir.domain.repositories.MainRepository
import ru.andrewkir.tinkofftestcase.common.BaseViewModel
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val repository: MainRepository) :
    BaseViewModel() {

    fun getMovie(filmID: Int): MovieModel {
        return repository.getMovie(filmID) ?: MovieModel()
    }
}