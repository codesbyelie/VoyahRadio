package elie.voyah.radio.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import elie.voyah.radio.app.theme.extraSmall
import elie.voyah.radio.app.theme.thin
import voyahradio.composeapp.generated.resources.Res
import voyahradio.composeapp.generated.resources.ic_bookmark
import voyahradio.composeapp.generated.resources.ic_bookmark_border

@Composable
fun RadioActionButtons(
    isSaved: Boolean,
    onSaveClick: () -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            Button(
                modifier = Modifier.padding(horizontal = thin).then(
                    if (isSaved) Modifier.width(120.dp) else Modifier.width(110.dp)
                ),
                onClick = {
                    onSaveClick()
                }
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = if (isSaved) painterResource(Res.drawable.ic_bookmark) else painterResource(
                        Res.drawable.ic_bookmark_border
                    ),
                    contentDescription = "Save"
                )
                Spacer(modifier = Modifier.width(extraSmall))
                Text(
                    text = if (isSaved) "Saved" else "Save"
                )
            }
        }
    }
}
