package app.migrosone.feature.news.domain.usecase

import app.migrosone.contract.ResultState
import app.migrosone.feature.news.domain.NewsRepository
import app.migrosone.feature.news.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(searchQuery: String? = null): Flow<ResultState<List<NewsArticle>>> {
        return repository.getNews(searchQuery)
    }
}
