package vahan.com.weatherate.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import vahan.com.weatherate.data.remote.NetworkManager
import vahan.com.weatherate.data.repository.WeatherRepository
import vahan.com.weatherate.data.repository.WeatherRepositoryImpl
import vahan.com.weatherate.ui.main.MainFragment
import vahan.com.weatherate.ui.WeatherViewModel
import vahan.com.weatherate.ui.search.SearchFragment

/**
 * Defines all the classes that need to be provided in the scope of the app.
 *
 * Define here all objects that are shared throughout the app, like SharedPreferences, navigators or
 * others.
 */

val appModule = module {
    single { NetworkManager.getApiService() }
    single { WeatherRepositoryImpl(get()) as WeatherRepository }

    viewModel { WeatherViewModel(get()) }

    scope(named<MainFragment>()) {
    }

    scope(named<SearchFragment>()) {
    }
}


