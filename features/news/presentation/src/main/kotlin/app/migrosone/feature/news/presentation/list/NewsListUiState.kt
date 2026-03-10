package app.migrosone.feature.news.presentation.list

import androidx.compose.runtime.Stable
import app.migrosone.feature.news.domain.model.NewsArticle

@Stable
sealed interface NewsListUiState {
    val isRefreshing: Boolean
    val articles: List<NewsArticle>

    data class Empty(
        override val isRefreshing: Boolean = false,
        override val articles: List<NewsArticle> = emptyList()
    ) : NewsListUiState

    data class Loading(
        override val isRefreshing: Boolean = true,
        override val articles: List<NewsArticle> = emptyList()
    ) : NewsListUiState

    data class Success(
        override val isRefreshing: Boolean = false,
        override val articles: List<NewsArticle>
    ) : NewsListUiState

    data class Error(
        override val isRefreshing: Boolean = false,
        val message: String,
        override val articles: List<NewsArticle> = emptyList()
    ) : NewsListUiState
}
