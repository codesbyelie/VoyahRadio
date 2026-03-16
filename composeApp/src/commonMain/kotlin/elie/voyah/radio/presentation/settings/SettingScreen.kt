package elie.voyah.radio.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import elie.voyah.radio.app.theme.medium
import androidx.navigation.NavController
import org.jetbrains.compose.resources.stringResource
import elie.voyah.radio.app.utils.Theme
import elie.voyah.radio.presentation.settings.components.ClearDataDialog
import elie.voyah.radio.presentation.settings.components.SettingItem
import elie.voyah.radio.presentation.settings.components.ThemeSelectionDialog
import voyahradio.composeapp.generated.resources.Res
import voyahradio.composeapp.generated.resources.clear_data
import voyahradio.composeapp.generated.resources.go_back
import voyahradio.composeapp.generated.resources.setting
import voyahradio.composeapp.generated.resources.station_grid_size
import voyahradio.composeapp.generated.resources.theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    rootNavController: NavController,
    viewModel: SettingViewModel
) {
    when {
        viewModel.isThemeDialog -> {
            ThemeSelectionDialog(
                currentTheme = viewModel.theme ?: Theme.SYSTEM_DEFAULT.name,
                onThemeChange = { theme ->
                    viewModel.onChangeTheme(theme)
                },
                onDismissRequest = {
                    viewModel.themeDialogToggle()
                }
            )
        }

        viewModel.isClearDataDialog -> {
            ClearDataDialog(
                onDeleteHistory = {
                    viewModel.onClearDataClick()
                },
                onDismissRequest = {
                    viewModel.clearDataDialogToggle()
                },
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.setting))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        rootNavController.navigateUp()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(Res.string.go_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            item {
                SettingItem(
                    onClick = {
                        viewModel.themeDialogToggle()
                    },
                    painter = Icons.Filled.Settings,
                    itemName = stringResource(Res.string.theme)
                )
            }
            item {
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = medium, vertical = medium)) {
                    Text(
                        text = stringResource(Res.string.station_grid_size),
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                    )
                    Slider(
                        value = viewModel.stationCellSizeFraction,
                        onValueChange = { viewModel.onStationCellSizeChange(it) },
                        valueRange = 0f..1f,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        thumb = {
                            SliderDefaults.Thumb(
                                interactionSource = remember { MutableInteractionSource() },
                                thumbSize = androidx.compose.ui.unit.DpSize(36.dp, 36.dp),
                                colors = SliderDefaults.colors()
                            )
                        }
                    )
                }
            }
            item {
                SettingItem(
                    onClick = {
                        viewModel.clearDataDialogToggle()
                    },
                    painter = Icons.Filled.Delete,
                    itemName = stringResource(Res.string.clear_data)
                )
            }
        }
    }
}