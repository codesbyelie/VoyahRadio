package elie.voyah.radio.app.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import elie.voyah.radio.presentation.settings.SettingViewModel

@Composable
fun NavigationScreenRoot(
    settingViewModel: SettingViewModel
) {
    val rootNavController = rememberNavController()
    Scaffold {
        NavHost(
            navController = rootNavController,
            startDestination = Route.Home,
        ) {
            navGraphBuilder(
                rootNavController = rootNavController,
                settingViewModel = settingViewModel
            )
        }
    }
}