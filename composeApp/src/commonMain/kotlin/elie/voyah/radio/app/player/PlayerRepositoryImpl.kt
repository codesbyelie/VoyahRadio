package elie.voyah.radio.app.player

import androidx.compose.runtime.Composable
import elie.voyah.radio.domain.Radio

class PlayerRepositoryImpl(
    private val playerController: PlayerController
) : PlayerRepository {
    override fun play(audioUrl: String) {
        playerController.play(audioUrl)
    }

    @Composable
    override fun PLayerUI(
        radio: Radio,
        isSaved: Boolean,
        onSaveClick: () -> Unit
    ) {
        playerController.PLayerUI(
            radio = radio,
            isSaved = isSaved,
            onSaveClick = onSaveClick
        )
    }

    override fun onCleared() {
        playerController.onCleared()
    }
}