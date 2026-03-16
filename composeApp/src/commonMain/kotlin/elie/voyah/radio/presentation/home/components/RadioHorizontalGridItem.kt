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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import elie.voyah.radio.domain.Radio
import elie.voyah.radio.app.theme.small
import elie.voyah.radio.app.theme.thin
import elie.voyah.radio.app.theme.zero

@Composable
fun RadioHorizontalGridItem(
    radios: List<Radio>,
    onRadioClick: (Radio) -> Unit,
    stationCellWidth: Dp,
    feedScale: Float,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val uniqueRadios = radios.distinctBy { it.id to it.url }
    val radiosSize by mutableStateOf(uniqueRadios.size)
    val imageMaxHeight = 180.dp
    // Scale text block height with feedScale so at max slider saved cards don't clip (title + country/language)
    val textBlockMinHeight = (52 * feedScale).coerceIn(52f, 72f).dp
    // Include card internal padding (Surface thin + Box small, top+bottom) so text block actually gets textBlockMinHeight
    val cardVerticalPadding = (thin.value * 2 + small.value * 2)
    val oneCardHeight = (minOf(stationCellWidth.value, imageMaxHeight.value) + small.value + textBlockMinHeight.value + cardVerticalPadding).dp
    val rowHeight = if (radios.size > 3) (oneCardHeight.value * 2).dp else oneCardHeight

    LazyHorizontalGrid(
        state = gridState,
        rows = GridCells.Fixed(if (radios.size > 3) 2 else 1),
        modifier = modifier
            .heightIn(max = rowHeight)
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
                modifier = Modifier.width(stationCellWidth)
            )
        }
    }
}