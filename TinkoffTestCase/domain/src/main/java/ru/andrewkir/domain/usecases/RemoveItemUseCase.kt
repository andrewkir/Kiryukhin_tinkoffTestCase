package ru.andrewkir.domain.usecases

import ru.andrewkir.domain.models.MovieModel
import ru.andrewkir.domain.repositories.MainRepository
import javax.inject.Inject

class RemoveItemUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke(item: MovieModel){
        return repository.removeItem(item)
    }
}