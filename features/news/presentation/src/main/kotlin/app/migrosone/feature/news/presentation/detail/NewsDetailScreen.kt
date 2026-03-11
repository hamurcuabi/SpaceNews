package app.migrosone.feature.news.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import app.migrosone.feature.news.domain.model.NewsArticle
import app.migrosone.feature.news.presentation.detail.states.NewsDetailErrorScreen
import app.migrosone.feature.news.presentation.detail.states.NewsDetailLoadingScreen
import app.migrosone.feature.news.presentation.detail.states.NewsDetailSuccessScreen
import app.migrosone.language.LocalStringResourceManager
import app.migrosone.language.ML
import app.migrosone.uikit.LocalCustomColorsPalette
import app.migrosone.uikit.OnLightCustomColorsPalette
import app.migrosone.uikit.components.scaffold.KtScaffold
import app.migrosone.uikit.components.text.KtText
import app.migrosone.uikit.components.toolbar.KtToolbar

@Composable
fun NewsDetailScreen(
    state: NewsDetailUiState,
    newsAction: NewsDetailAction
) {
    val color = LocalCustomColorsPalette.current
    val stringResourceManager = LocalStringResourceManager.current

    KtScaffold(
        topBar = {
            KtToolbar(
                startContent = {
                    IconButton(onClick = newsAction::onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResourceManager[ML::backButtonDescription],
                            tint = Color.White
                        )
                    }
                },
                centerContent = {
                    KtText(
                        text = stringResourceManager[ML::newsDetailTitle],
                        color = color.textWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                endContent = {
                    if (state is NewsDetailUiState.Success) {
                        Row {
                            IconButton(onClick = {
                                newsAction.onShareClick(state.article.title, state.article.url)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = stringResourceManager[ML::shareButtonDescription],
                                    tint = Color.White
                                )
                            }
                            IconButton(onClick = newsAction::onToggleFavorite) {
                                Icon(
                                    imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = stringResourceManager[ML::favoriteButtonDescription],
                                    tint = if (state.isFavorite) Color.Red else Color.White
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (state) {
                is NewsDetailUiState.Loading -> {
                    NewsDetailLoadingScreen(color = color)
                }

                is NewsDetailUiState.Success -> {
                    NewsDetailSuccessScreen(
                        article = state.article,
                        color = color,
                        newsAction = newsAction,
                        stringResourceManager = stringResourceManager
                    )
                }

                is NewsDetailUiState.Error -> {
                    NewsDetailErrorScreen(
                        message = state.message,
                        color = color
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsDetailScreenPreview() {
    val dummyArticle = NewsArticle(
        1,
        "Title",
        "https://example.com",
        "https://example.com/img",
        "Site",
        "Summary",
        "2024-01-01",
        true
    )
    val dummyState = NewsDetailUiState.Success(dummyArticle, true)
    val dummyAction = object : NewsDetailAction {
        override fun onBackClick() {}
        override fun onToggleFavorite() {}
        override fun onShareClick(title: String, url: String) {}
        override fun onOpenUrl(url: String) {}
    }

    CompositionLocalProvider(
        LocalCustomColorsPalette provides OnLightCustomColorsPalette
    ) {
        NewsDetailScreen(
            state = dummyState,
            newsAction = dummyAction
        )
    }
}

