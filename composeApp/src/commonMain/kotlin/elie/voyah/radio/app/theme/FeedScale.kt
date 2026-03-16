package elie.voyah.radio.app.theme

import androidx.compose.runtime.compositionLocalOf

/**
 * Scale factor for station feed items (icons + text). 1f = normal; >1f = larger (fewer per row); <1f = smaller (more per row).
 * Provided by HomeScreen from the Settings slider value.
 */
val LocalFeedScale = compositionLocalOf { 1f }
