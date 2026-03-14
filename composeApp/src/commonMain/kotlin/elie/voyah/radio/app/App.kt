package elie.voyah.radio.app

import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import elie.voyah.radio.app.navigation.NavigationScreenRoot
import elie.voyah.radio.app.theme.AppTheme
import elie.voyah.radio.presentation.settings.SettingViewModel

@Composable
fun App() {
    KoinContext {
        val settingViewModel = koinViewModel<SettingViewModel>()
        val currentTheme = settingViewModel.theme
        AppTheme(currentTheme) {
            NavigationScreenRoot(
                settingViewModel = settingViewModel
            )
        }
    }
}