package ru.andrewkir.tinkofftestcase.flows.favourites

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.andrewkir.domain.models.MovieModel
import ru.andrewkir.domain.repositories.MainRepository
import ru.andrewkir.tinkofftestcase.common.BaseViewModel
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(private val repository: MainRepository) :
    BaseViewModel(
    ) {
    fun getData(): List<MovieModel> {
        return repository.getFavouritesMovies()
    }

    fun removeItem(item: MovieModel?) {
        if (item == null) return
        viewModelScope.launch {
            repository.removeItem(item)
        }
    }


    init {
        getData()
    }
}