package elie.voyah.radio.presentation.home.components

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import elie.voyah.radio.domain.Radio
import elie.voyah.radio.app.theme.LocalFeedScale
import elie.voyah.radio.app.theme.horizontalGridMaxHeight
import elie.voyah.radio.app.theme.horizontalGridMaxWidth
import elie.voyah.radio.app.theme.zero

@Composable
fun RadioHorizontalGridItem(
    radios: List<Radio>,
    onRadioClick: (Radio) -> Unit,
    modifier: Modifier = Modifier
) {
    val feedScale = LocalFeedScale.current
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val uniqueRadios = radios.distinctBy { it.id to it.url }
    val radiosSize by mutableStateOf(uniqueRadios.size)
    val scaledWidth = (horizontalGridMaxWidth.value * feedScale).dp
    val scaledMaxHeight = (horizontalGridMaxHeight.value * feedScale).dp
    val scaledMaxHeightHalf = (horizontalGridMaxHeight.value * 0.5f * feedScale).dp

    LazyHorizontalGrid(
        state = gridState,
        rows = GridCells.Fixed(if (radios.size > 3) 2 else 1),
        modifier = modifier
            .heightIn(max = if (radios.size > 3) scaledMaxHeight else scaledMaxHeightHalf)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {
                        gridState.scrollBy(-dragAmount)
                    }
                }
            },
        verticalArrangement = Arrangement.spacedBy(zero),
        horizontalArrangement = Arrangement.spacedBy(zero)
    ) {
        items(radiosSize, key = { uniqueRadios[it].id }, contentType = { "radios" }) {
            RadioGridItem(
                radio = uniqueRadios[it],
                onClick = {
                    onRadioClick(uniqueRadios[it])
                },
                modifier = Modifier.width(scaledWidth)
            )
        }
    }
}