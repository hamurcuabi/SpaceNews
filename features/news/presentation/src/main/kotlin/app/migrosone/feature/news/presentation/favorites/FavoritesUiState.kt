package app.migrosone.feature.news.presentation.favorites

import app.migrosone.feature.news.domain.model.NewsArticle

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState

    data object Empty : FavoritesUiState

    data class Success(
        val favorites: List<NewsArticle>
    ) : FavoritesUiState
}
