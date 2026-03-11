package app.migrosone.feature.news.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.migrosone.contract.ResultState
import app.migrosone.feature.news.domain.model.NewsArticle
import app.migrosone.feature.news.domain.usecase.GetNewsUseCase
import app.migrosone.feature.news.presentation.navigation.NewsNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNews: GetNewsUseCase,
    private val newsNavigation: NewsNavigation
) : ViewModel(), NewsListAction {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    private val _refreshRequestTime = MutableStateFlow(System.currentTimeMillis())

    @OptIn(FlowPreview::class)
    val uiState: StateFlow<NewsListUiState> = combine(
        _searchQuery.debounce(500).distinctUntilChanged(),
        _refreshRequestTime
    ) { query, _ -> query }
        .flatMapLatest { query ->
            getNews(query.takeIf { it.isNotBlank() })
        }
        .map { result ->
            when (result) {
                is ResultState.Loading -> NewsListUiState.Loading()

                is ResultState.Success -> {
                    if (result.data.isEmpty()) {
                        NewsListUiState.Empty()
                    } else {
                        NewsListUiState.Success(
                            articles = result.data
                        )
                    }
                }

                is ResultState.Error -> NewsListUiState.Error(
                    message = result.appException.message.orEmpty()
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = NewsListUiState.Loading()
        )

    override fun onSearchQueryChanged(query: String) {
        _searchQuery.update { query }
    }

    override fun onArticleClick(article: NewsArticle) {
        newsNavigation.navigateToDetailScreen(article)
    }

    override fun onRefresh() {
        viewModelScope.launch {
            _refreshRequestTime.value = System.currentTimeMillis()
        }
    }

    override fun navigateToFavorites() {
        newsNavigation.navigateToFavoritesScreen()
    }
}
