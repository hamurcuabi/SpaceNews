package app.migrosone.feature.news.presentation.favorites

import androidx.compose.runtime.Stable
import app.migrosone.feature.news.domain.model.NewsArticle

@Stable
sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState

    data object Empty : FavoritesUiState

    data class Success(
        val favorites: List<NewsArticle>
    ) : FavoritesUiState
}
