package app.migrosone.feature.news.domain.usecase

import app.migrosone.feature.news.domain.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> {
        return repository.isFavorite(id)
    }
}
