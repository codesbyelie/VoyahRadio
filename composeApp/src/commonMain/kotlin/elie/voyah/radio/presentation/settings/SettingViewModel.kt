package elie.voyah.radio.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import elie.voyah.radio.app.utils.AppPreferences
import elie.voyah.radio.app.utils.Theme
import elie.voyah.radio.data.database.VoyahRadioDatabase

class SettingViewModel(
    private val appPreferences: AppPreferences,
    private val voyahRadioDatabase: VoyahRadioDatabase,
) : ViewModel() {

    var theme by mutableStateOf<String?>(appPreferences.getThemeSync())
        private set

    var isThemeDialog by mutableStateOf(false)
        private set

    var isClearDataDialog by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            theme = appPreferences.getTheme()
        }
    }

    fun themeDialogToggle() {
        isThemeDialog = !isThemeDialog
    }

    fun clearDataDialogToggle() {
        isClearDataDialog = !isClearDataDialog
    }

    fun onClearDataClick() {
        viewModelScope.launch(Dispatchers.IO) {
            voyahRadioDatabase.clearAllEntities()
            clearDataDialogToggle()
        }
    }

    fun onChangeTheme(theme: Theme) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                appPreferences.changeThemeMode(theme.name)
            }
            this@SettingViewModel.theme = theme.name
            themeDialogToggle()
        }
    }
}