package elie.voyah.radio.app.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import voyahradio.composeapp.generated.resources.Poppins_100
import voyahradio.composeapp.generated.resources.Poppins_200
import voyahradio.composeapp.generated.resources.Poppins_300
import voyahradio.composeapp.generated.resources.Poppins_400
import voyahradio.composeapp.generated.resources.Poppins_500
import voyahradio.composeapp.generated.resources.Poppins_600
import voyahradio.composeapp.generated.resources.Poppins_700
import voyahradio.composeapp.generated.resources.Poppins_800
import voyahradio.composeapp.generated.resources.Poppins_900
import voyahradio.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun PoppinsFontFamily() = FontFamily(
    Font(Res.font.Poppins_100, weight = FontWeight.Thin),
    Font(Res.font.Poppins_200, weight = FontWeight.ExtraLight),
    Font(Res.font.Poppins_300, weight = FontWeight.Light),
    Font(Res.font.Poppins_400, weight = FontWeight.Normal),
    Font(Res.font.Poppins_500, weight = FontWeight.Medium),
    Font(Res.font.Poppins_600, weight = FontWeight.SemiBold),
    Font(Res.font.Poppins_700, weight = FontWeight.Bold),
    Font(Res.font.Poppins_800, weight = FontWeight.ExtraBold),
    Font(Res.font.Poppins_900, weight = FontWeight.Black),
)

// Scale factor for headunit/car readability (matches ~50% larger icons)
private const val TYPOGRAPHY_SCALE = 1.5f

@Composable
fun AppTypography() = Typography().run {
    val fontFamily = PoppinsFontFamily()
    fun scaleSp(style: TextStyle) =
        (style.fontSize.value * TYPOGRAPHY_SCALE).sp
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily, fontSize = scaleSp(displayLarge)),
        displayMedium = displayMedium.copy(fontFamily = fontFamily, fontSize = scaleSp(displayMedium)),
        displaySmall = displaySmall.copy(fontFamily = fontFamily, fontSize = scaleSp(displaySmall)),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily, fontSize = scaleSp(headlineLarge)),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily, fontSize = scaleSp(headlineMedium)),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily, fontSize = scaleSp(headlineSmall)),
        titleLarge = titleLarge.copy(fontFamily = fontFamily, fontSize = scaleSp(titleLarge)),
        titleMedium = titleMedium.copy(fontFamily = fontFamily, fontSize = scaleSp(titleMedium)),
        titleSmall = titleSmall.copy(fontFamily = fontFamily, fontSize = scaleSp(titleSmall)),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily, fontSize = scaleSp(bodyLarge)),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily, fontSize = scaleSp(bodyMedium)),
        bodySmall = bodySmall.copy(fontFamily = fontFamily, fontSize = scaleSp(bodySmall)),
        labelLarge = labelLarge.copy(fontFamily = fontFamily, fontSize = scaleSp(labelLarge)),
        labelMedium = labelMedium.copy(fontFamily = fontFamily, fontSize = scaleSp(labelMedium)),
        labelSmall = labelSmall.copy(fontFamily = fontFamily, fontSize = scaleSp(labelSmall))
    )
}