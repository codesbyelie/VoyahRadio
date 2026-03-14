package elie.voyah.radio.app.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import elie.voyah.radio.app.player.PlayerRepository
import elie.voyah.radio.app.player.PlayerRepositoryImpl
import elie.voyah.radio.app.utils.AppPreferences
import elie.voyah.radio.app.utils.HttpClientFactory
import elie.voyah.radio.data.database.DatabaseFactory
import elie.voyah.radio.data.database.VoyahRadioDatabase
import elie.voyah.radio.data.network.RemoteRadioDataSource
import elie.voyah.radio.data.network.RemoteRadioDataSourceImpl
import elie.voyah.radio.data.repository.RadioRepositoryImpl
import elie.voyah.radio.domain.RadioRepository
import elie.voyah.radio.presentation.home.HomeViewModel
import elie.voyah.radio.presentation.settings.SettingViewModel

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    single { get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build() }
    single { get<VoyahRadioDatabase>().voyahRadioDao }
    singleOf(::RemoteRadioDataSourceImpl).bind<RemoteRadioDataSource>()
    singleOf(::RadioRepositoryImpl).bind<RadioRepository>()
    singleOf(::PlayerRepositoryImpl).bind<PlayerRepository>()
    singleOf(::AppPreferences)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SettingViewModel)
}