package app.migrosone.feature.news.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.migrosone.feature.news.domain.model.NewsArticle
import app.migrosone.feature.news.domain.usecase.GetFavoriteNewsUseCase
import app.migrosone.feature.news.domain.usecase.ToggleFavoriteUseCase
import app.migrosone.feature.news.presentation.navigation.NewsNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteNewsUseCase: GetFavoriteNewsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val navigation: NewsNavigation
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesUiState>(
        FavoritesUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getFavoriteNewsUseCase().collect {
                val state = if (it.isEmpty()) FavoritesUiState.Empty
                else FavoritesUiState.Success(it)
                _uiState.update { state }
            }
        }
    }

    fun onArticleClick(article: NewsArticle) {
        navigation.navigateToDetailScreen(article)
    }

    fun onToggleFavorite(article: NewsArticle) {
        viewModelScope.launch {
            toggleFavoriteUseCase(article)
        }
    }

    fun onBackClick() {
        navigation.navigateUp()
    }
}
