package ru.andrewkir.tinkofftestcase

import android.app.Application
import ru.andrewkir.data.di.RetrofitModule
import ru.andrewkir.tinkofftestcase.di.*

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .dataBaseModule(DataBaseModule(context = this))
            .appModule(AppModule(context = this))
            .retrofitModule(RetrofitModule("https://kinopoiskapiunofficial.tech/"))
            .build()
    }
}