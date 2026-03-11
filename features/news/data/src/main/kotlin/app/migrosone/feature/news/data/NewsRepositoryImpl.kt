package app.migrosone.feature.news.data

import app.migrosone.contract.AppException
import app.migrosone.contract.ResultState
import app.migrosone.database.room.room.dao.NewsDao
import app.migrosone.database.room.room.db.AppDatabase
import app.migrosone.feature.news.data.mapper.NewsMapper
import app.migrosone.feature.news.data.remote.NewsApi
import app.migrosone.feature.news.domain.NewsRepository
import app.migrosone.feature.news.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val newsDao: NewsDao,
    private val database: AppDatabase,
    private val mapper: NewsMapper
) : NewsRepository {

    override fun getNews(searchQuery: String?): Flow<ResultState<List<NewsArticle>>> = flow {
        emit(ResultState.Loading)
        try {
            val response = api.getNews(limit = 50, search = searchQuery)
            val articles = response.results.map { mapper.dtoToDomain(it) }

            database.cachedNewsDao().clearAll()
            database.cachedNewsDao().insertAll(articles.map { mapper.domainToCached(it) })

            emitAll(combineWithFavorites(articles))
        } catch (e: Exception) {
            val appException = AppException.from(e)
            Timber.e(appException, "Error while fetching regular news")
            val cachedList = database.cachedNewsDao().getAllCachedNewsList()
            if (cachedList.isNotEmpty()) {
                val articles = cachedList.map { mapper.cachedToDomain(it) }
                emitAll(combineWithFavorites(articles))
            } else {
                emit(ResultState.Error(appException))
            }
        }
    }

    private fun combineWithFavorites(articles: List<NewsArticle>): Flow<ResultState<List<NewsArticle>>> {
        return newsDao.getAllFavoriteIds().map { favoriteIds ->
            val articlesWithFavorites = articles.map { article ->
                val isFavorite = favoriteIds.contains(article.id)
                if (article.isFavorite == isFavorite) article else article.copy(isFavorite = isFavorite)
            }
            ResultState.Success(articlesWithFavorites)
        }
    }

    override fun getFavoriteNews(): Flow<List<NewsArticle>> {
        return newsDao.getAllFavorites().map { entities ->
            entities.map { mapper.entityToDomain(it) }
        }
    }

    override suspend fun toggleFavorite(article: NewsArticle) {
        val existing = newsDao.getFavoriteById(article.id)
        if (existing != null) {
            newsDao.deleteFavorite(existing)
        } else {
            newsDao.insertFavorite(mapper.domainToEntity(article))
        }
    }

    override fun isFavorite(id: Int): Flow<Boolean> {
        return newsDao.isFavorite(id)
    }

    override fun getNewsDetail(id: Int): Flow<ResultState<NewsArticle>> = flow {
        emit(ResultState.Loading)
        try {
            val response = api.getNewsDetail(id)
            emit(ResultState.Success(mapper.dtoToDomain(response)))
        } catch (e: Exception) {
            val appException = AppException.from(e)
            Timber.e(appException, "Error while fetching news detail for id $id")
            val cachedEntity = database.cachedNewsDao().getCachedNewsById(id)
            if (cachedEntity != null) {
                emit(ResultState.Success(mapper.cachedToDomain(cachedEntity)))
            } else {
                val favoriteEntity = newsDao.getFavoriteById(id)
                if (favoriteEntity != null) {
                    emit(ResultState.Success(mapper.entityToDomain(favoriteEntity)))
                } else {
                    emit(ResultState.Error(appException))
                }
            }
        }
    }

}
