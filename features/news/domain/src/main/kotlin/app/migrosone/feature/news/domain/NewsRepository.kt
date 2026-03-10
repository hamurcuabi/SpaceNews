package app.migrosone.feature.news.domain

import app.migrosone.contract.ResultState
import app.migrosone.feature.news.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(searchQuery: String? = null): Flow<ResultState<List<NewsArticle>>>

    fun getFavoriteNews(): Flow<List<NewsArticle>>

    suspend fun toggleFavorite(article: NewsArticle)

    fun isFavorite(id: Int): Flow<Boolean>

    fun getNewsDetail(id: Int): Flow<ResultState<NewsArticle>>
}
