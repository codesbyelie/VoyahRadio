package elie.voyah.radio.app.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AppPreferences(
    private val dataStore: DataStore<Preferences>
) {
    private val themeKey = stringPreferencesKey("theme")
    private val stationCellSizeFractionKey = floatPreferencesKey("station_cell_size_fraction")

    /** 0f = smaller cells (more per row), 1f = larger cells (fewer per row). Default 0.5f. */
    fun getStationCellSizeFraction() = dataStore.data.map { prefs ->
        prefs[stationCellSizeFractionKey] ?: 0.5f
    }

    fun getStationCellSizeFractionSync(): Float = runBlocking {
        getStationCellSizeFraction().first()
    }

    suspend fun setStationCellSizeFraction(value: Float) = dataStore.edit { prefs ->
        prefs[stationCellSizeFractionKey] = value.coerceIn(0f, 1f)
    }

    suspend fun getTheme() = dataStore.data.map { preferences ->
        preferences[themeKey] ?: Theme.LIGHT_MODE.name
    }.first()

    fun getThemeSync(): String {
        return runBlocking {
            getTheme()
        }
    }
    suspend fun changeThemeMode(value: String) = dataStore.edit { preferences ->
        preferences[themeKey] = value
    }
}