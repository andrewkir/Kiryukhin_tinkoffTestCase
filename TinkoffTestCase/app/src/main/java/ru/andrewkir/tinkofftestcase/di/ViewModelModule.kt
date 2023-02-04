package ru.andrewkir.tinkofftestcase.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.andrewkir.tinkofftestcase.common.ViewModelFactory
import ru.andrewkir.tinkofftestcase.flows.details.DetailsViewModel
import ru.andrewkir.tinkofftestcase.flows.main.PopularViewModel

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PopularViewModel::class)
    abstract fun mainViewModel(viewModel: PopularViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun detailsViewModel(viewModel: DetailsViewModel): ViewModel
}