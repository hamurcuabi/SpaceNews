package app.migrosone.feature.news.presentation.detail.states

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.migrosone.uikit.CustomColorsPalette

@Composable
internal fun NewsDetailLoadingScreen(color: CustomColorsPalette) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = color.primaryColor)
    }
}