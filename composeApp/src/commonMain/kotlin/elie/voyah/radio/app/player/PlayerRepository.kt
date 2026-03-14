package elie.voyah.radio.app.player

import androidx.compose.runtime.Composable
import elie.voyah.radio.domain.Radio

interface PlayerRepository {
    fun play(audioUrl: String)
    @Composable
    fun PLayerUI(
        radio: Radio,
        isSaved: Boolean,
        onSaveClick: () -> Unit
    )
    fun onCleared()
}