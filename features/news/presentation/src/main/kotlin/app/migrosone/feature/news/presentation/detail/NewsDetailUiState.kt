package app.migrosone.feature.news.presentation.detail

import app.migrosone.feature.news.domain.model.NewsArticle

sealed interface NewsDetailUiState {

    data object Loading : NewsDetailUiState

    data class Success(
        val article: NewsArticle,
        val isFavorite: Boolean
    ) : NewsDetailUiState

    data class Error(
        val message: String
    ) : NewsDetailUiState
}
