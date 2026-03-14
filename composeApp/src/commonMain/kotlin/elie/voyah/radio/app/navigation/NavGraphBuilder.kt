package elie.voyah.radio.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import elie.voyah.radio.presentation.home.HomeScreen
import elie.voyah.radio.presentation.settings.SettingScreen
import elie.voyah.radio.presentation.settings.SettingViewModel

fun NavGraphBuilder.navGraphBuilder(
    rootNavController: NavController,
    settingViewModel: SettingViewModel
) {
    composable<Route.Home> {
        HomeScreen(
            rootNavController = rootNavController
        )
    }
    composable<Route.Settings> {
        SettingScreen(
            viewModel = settingViewModel,
            rootNavController = rootNavController
        )
    }
}