package app.migrosone.feature.news.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.migrosone.feature.news.domain.model.NewsArticle
import app.migrosone.feature.news.presentation.list.states.NewsListEmptyScreen
import app.migrosone.feature.news.presentation.list.states.NewsListErrorScreen
import app.migrosone.feature.news.presentation.list.states.NewsListLoadingScreen
import app.migrosone.feature.news.presentation.list.states.NewsListSuccessScreen
import app.migrosone.language.LocalStringResourceManager
import app.migrosone.language.ML
import app.migrosone.uikit.LocalCustomColorsPalette
import app.migrosone.uikit.OnLightCustomColorsPalette
import app.migrosone.uikit.components.scaffold.KtScaffold
import app.migrosone.uikit.components.text.KtText
import app.migrosone.uikit.components.toolbar.KtToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewsListScreen(
    searchQuery: String,
    state: NewsListUiState,
    action: NewsListAction
) {
    val color = LocalCustomColorsPalette.current
    val stringResourceManager = LocalStringResourceManager.current
    val pullToRefreshState = rememberPullToRefreshState()

    KtScaffold(
        topBar = {
            KtToolbar(
                centerContent = {
                    KtText(
                        text = stringResourceManager[ML::newsListTitle],
                        color = color.textWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                endContent = {
                    IconButton(onClick = action::navigateToFavorites) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorites",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color.backgroundColor)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = action::onSearchQueryChanged,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResourceManager[ML::newsSearchPlaceholder],
                            color = color.textGray.copy(alpha = 0.6f),
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = color.primaryColor
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = color.primaryColor
                    )
                )
            }

            PullToRefreshBox(
                modifier = Modifier.fillMaxSize(),
                state = pullToRefreshState,
                isRefreshing = state.isRefreshing,
                onRefresh = { action.onRefresh() },
            ) {
                when (state) {
                    is NewsListUiState.Loading -> {
                        NewsListLoadingScreen()
                    }

                    is NewsListUiState.Error -> {
                        NewsListErrorScreen(
                            message = state.message,
                            onRetry = action::onRefresh,
                            stringResourceManager = stringResourceManager,
                            color = color
                        )
                    }

                    is NewsListUiState.Success -> {
                        NewsListSuccessScreen(
                            articles = state.articles,
                            action = action
                        )
                    }

                    is NewsListUiState.Empty -> {
                        NewsListEmptyScreen(
                            stringResourceManager = stringResourceManager
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsListScreenPreview() {
    val dummyArticles = listOf(
        NewsArticle(
            1,
            "Title 1",
            "https://example.com/1",
            "https://example.com/img1",
            "Site",
            "Summary 1",
            "2024-01-01",
            false
        ),
        NewsArticle(
            2,
            "Title 2",
            "https://example.com/2",
            "https://example.com/img2",
            "Site",
            "Summary 2",
            "2024-01-02",
            true
        )
    )
    val dummyState = NewsListUiState.Success(false, dummyArticles)
    val dummyAction = object : NewsListAction {
        override fun onSearchQueryChanged(query: String) {}
        override fun onRefresh() {}
        override fun onArticleClick(article: NewsArticle) {}
        override fun navigateToFavorites() {}
    }

    CompositionLocalProvider(
        LocalCustomColorsPalette provides OnLightCustomColorsPalette
    ) {
        NewsListScreen(
            searchQuery = "",
            state = dummyState,
            action = dummyAction
        )
    }
}