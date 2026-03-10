package app.migrosone.feature.news.data.mapper

import app.migrosone.database.room.room.entity.CachedNewsEntity
import app.migrosone.database.room.room.entity.NewsEntity
import app.migrosone.feature.news.data.remote.model.ArticleDto
import app.migrosone.feature.news.domain.model.NewsArticle
import javax.inject.Inject

class NewsMapper @Inject constructor(
    private val dateFormatter: DateFormatter
) {

    fun dtoToDomain(dto: ArticleDto): NewsArticle = NewsArticle(
        id = dto.id ?: 0,
        title = dto.title.orEmpty(),
        summary = dto.summary.orEmpty(),
        imageUrl = dto.imageUrl.orEmpty(),
        publishedAt = dto.publishedAt.orEmpty(),
        displayDate = dateFormatter.formatToHumanReadable(dto.publishedAt),
        url = dto.url.orEmpty()
    )

    fun entityToDomain(entity: NewsEntity): NewsArticle = NewsArticle(
        id = entity.id,
        title = entity.title,
        summary = entity.summary,
        imageUrl = entity.imageUrl,
        publishedAt = entity.publishedAt,
        displayDate = dateFormatter.formatToHumanReadable(entity.publishedAt),
        url = entity.url,
        isFavorite = true
    )

    fun domainToEntity(article: NewsArticle): NewsEntity = NewsEntity(
        id = article.id,
        title = article.title,
        summary = article.summary,
        imageUrl = article.imageUrl,
        publishedAt = article.publishedAt,
        url = article.url
    )

    fun cachedToDomain(entity: CachedNewsEntity): NewsArticle = NewsArticle(
        id = entity.id,
        title = entity.title,
        summary = entity.summary,
        imageUrl = entity.imageUrl,
        publishedAt = entity.publishedAt,
        displayDate = dateFormatter.formatToHumanReadable(entity.publishedAt),
        url = entity.url
    )


    fun domainToCached(article: NewsArticle): CachedNewsEntity = CachedNewsEntity(
        id = article.id,
        title = article.title,
        summary = article.summary,
        imageUrl = article.imageUrl,
        publishedAt = article.publishedAt,
        url = article.url
    )
}
