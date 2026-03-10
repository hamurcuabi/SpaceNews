package app.migrosone.feature.news.domain.usecase

import app.migrosone.feature.news.domain.NewsRepository
import app.migrosone.feature.news.domain.model.NewsArticle
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(article: NewsArticle) {
        repository.toggleFavorite(article)
    }
}
