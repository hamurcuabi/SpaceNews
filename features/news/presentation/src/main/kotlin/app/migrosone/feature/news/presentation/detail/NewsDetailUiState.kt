package app.migrosone.feature.news.presentation.detail

import androidx.compose.runtime.Stable
import app.migrosone.feature.news.domain.model.NewsArticle

@Stable
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
