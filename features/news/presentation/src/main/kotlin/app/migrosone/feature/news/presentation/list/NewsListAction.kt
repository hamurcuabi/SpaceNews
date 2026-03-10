package app.migrosone.feature.news.presentation.list

import app.migrosone.feature.news.domain.model.NewsArticle

interface NewsListAction {
    fun onSearchQueryChanged(query: String)
    fun onArticleClick(article: NewsArticle)
    fun onRefresh()
    fun navigateToFavorites()
}
