package app.migrosone.feature.news.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.migrosone.feature.news.domain.model.NewsArticle
import app.migrosone.feature.news.presentation.component.NewsItemCompose
import app.migrosone.language.LocalStringResourceManager
import app.migrosone.language.ML
import app.migrosone.language.StringResourceManager
import app.migrosone.uikit.CustomColorsPalette
import app.migrosone.uikit.LocalCustomColorsPalette
import app.migrosone.uikit.components.scaffold.KtScaffold
import app.migrosone.uikit.components.text.KtText
import app.migrosone.uikit.components.toolbar.KtToolbar

@Composable
fun FavoritesScreen(
    state: FavoritesUiState,
    onArticleClick: (NewsArticle) -> Unit,
    onBackClick: () -> Unit
) {
    val color = LocalCustomColorsPalette.current
    val stringResourceManager = LocalStringResourceManager.current

    KtScaffold(
        topBar = {
            KtToolbar(
                startContent = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResourceManager[ML::backButtonDescription],
                            tint = Color.White
                        )
                    }
                },
                centerContent = {
                    KtText(
                        text = stringResourceManager[ML::favoriteListTitle],
                        color = color.textWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
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
                is FavoritesUiState.Loading -> {
                    FavoritesLoadingScreen(color = color)
                }

                is FavoritesUiState.Success -> {
                    FavoritesSuccessScreen(
                        favorites = state.favorites,
                        onArticleClick = onArticleClick,
                        color = color
                    )
                }

                is FavoritesUiState.Empty -> {
                    FavoritesEmptyScreen(
                        stringResourceManager = stringResourceManager,
                        color = color
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoritesLoadingScreen(color: CustomColorsPalette) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = color.primaryColor)
    }
}

@Composable
private fun FavoritesSuccessScreen(
    favorites: List<NewsArticle>,
    onArticleClick: (NewsArticle) -> Unit,
    color: CustomColorsPalette
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color.backgroundColor),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(favorites, key = { it.id }) { article ->
            NewsItemCompose(
                article = article,
                onClick = { onArticleClick(article) },
            )
        }
    }
}

@Composable
private fun FavoritesEmptyScreen(
    stringResourceManager: StringResourceManager,
    color: CustomColorsPalette
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        KtText(
            text = stringResourceManager[ML::favoriteListEmpty],
            fontSize = 16.sp,
            color = color.textGray
        )
    }
}
