package ru.andrewkir.tinkofftestcase.di

import androidx.viewbinding.ViewBinding
import dagger.Component
import ru.andrewkir.data.di.RetrofitModule
import ru.andrewkir.tinkofftestcase.common.BaseFragment
import ru.andrewkir.tinkofftestcase.common.BaseViewModel
import ru.andrewkir.tinkofftestcase.flows.details.DetailsFragment
import ru.andrewkir.tinkofftestcase.flows.main.PopularFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, DataModule::class, AppModule::class, DataBaseModule::class, RetrofitModule::class])
interface AppComponent {
    fun inject(baseFragment: BaseFragment<BaseViewModel, ViewBinding>)
    fun inject(mainFragment: PopularFragment)
    fun inject(detailsFragment: DetailsFragment)
}