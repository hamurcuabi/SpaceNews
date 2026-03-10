package app.migrosone.feature.news.presentation.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FavoritesRouteScreen(viewModel: FavoritesViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoritesScreen(
        state = uiState,
        onArticleClick = viewModel::onArticleClick,
        onBackClick = viewModel::onBackClick
    )
}
