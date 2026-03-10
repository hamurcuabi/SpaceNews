package app.migrosone.feature.news.presentation.list

import androidx.compose.runtime.Stable
import app.migrosone.feature.news.domain.model.NewsArticle

@Stable
interface NewsListAction {
    fun onSearchQueryChanged(query: String)
    fun onArticleClick(article: NewsArticle)
    fun onRefresh()
    fun navigateToFavorites()
}
