package app.migrosone.uikit

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

@Suppress("FunctionParameterArgumentRule")
val UiKitFontFamily = FontFamily(
    Font(R.font.open_sans_light, weight = FontWeight.Light),
    Font(R.font.open_sans_regular, weight = FontWeight.Normal),
    Font(R.font.open_sans_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.open_sans_bold, weight = FontWeight.Bold)
)
