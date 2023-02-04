package ru.andrewkir.tinkofftestcase.flows.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.andrewkir.domain.models.MovieModel
import ru.andrewkir.domain.repositories.MainRepository
import ru.andrewkir.tinkofftestcase.common.BaseViewModel
import javax.inject.Inject

class PopularViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel(
) {
    fun getData(): Flow<PagingData<MovieModel>>? {
        return repository.getMoviesList()
    }

    fun removeItem(item: MovieModel) {
        viewModelScope.launch {
            repository.removeItem(item)
        }
    }

    fun addItem(text: String) {
        viewModelScope.launch {
            repository.addItem(MovieModel())
        }
    }

    fun updateItem(item: MovieModel?) {
        if (item == null) return
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

    init {
        getData()
    }
}