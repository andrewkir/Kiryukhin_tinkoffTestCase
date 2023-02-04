package ru.andrewkir.domain.usecases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.andrewkir.domain.models.MovieModel
import ru.andrewkir.domain.repositories.MainRepository
import javax.inject.Inject

class GetMoviesListUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke(): Flow<PagingData<MovieModel>>? {
        return repository.getMoviesList()
    }
}