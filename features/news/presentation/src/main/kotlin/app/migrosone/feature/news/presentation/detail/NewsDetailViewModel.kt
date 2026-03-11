package app.migrosone.feature.news.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.migrosone.contract.ResultState
import app.migrosone.core.presentation.util.IntentUtils
import app.migrosone.feature.news.contract.NewsDetailArgs
import app.migrosone.feature.news.domain.model.NewsArticle
import app.migrosone.feature.news.domain.usecase.GetNewsDetailUseCase
import app.migrosone.feature.news.domain.usecase.IsFavoriteUseCase
import app.migrosone.feature.news.domain.usecase.ToggleFavoriteUseCase
import app.migrosone.feature.news.presentation.navigation.NewsNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val getNewsDetail: GetNewsDetailUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
    private val isFavorite: IsFavoriteUseCase,
    private val newsNavigation: NewsNavigation,
    private val intentUtils: IntentUtils
) : ViewModel(), NewsDetailAction {

    private val _uiState = MutableStateFlow<NewsDetailUiState>(
        NewsDetailUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private var currentArticle: NewsArticle? = null

    fun initialize(args: NewsDetailArgs) {
        viewModelScope.launch {
            combine(
                getNewsDetail(args.id),
                isFavorite(args.id)
            ) { result, isFav ->
                when (result) {
                    is ResultState.Success -> {
                        currentArticle = result.data
                        NewsDetailUiState.Success(result.data, isFav)
                    }

                    is ResultState.Error -> NewsDetailUiState.Error(
                        result.appException.message.orEmpty()
                    )

                    ResultState.Loading -> NewsDetailUiState.Loading
                }
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    override fun onBackClick() {
        newsNavigation.navigateUp()
    }

    override fun onToggleFavorite() {
        currentArticle?.let { article ->
            viewModelScope.launch {
                toggleFavorite(article)
            }
        }
    }

    override fun onShareClick(title: String, url: String) {
        intentUtils.shareText(
            "${title}\n\n${url}"
        )
    }

    override fun onOpenUrl(url: String) {
        intentUtils.openBrowser(url)
    }
}
