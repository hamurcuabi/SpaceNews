package app.migrosone.feature.news.presentation.list.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.migrosone.feature.news.domain.model.NewsArticle
import app.migrosone.feature.news.presentation.component.NewsItemCompose
import app.migrosone.feature.news.presentation.list.NewsListAction

@Composable
internal fun NewsListSuccessScreen(
    articles: List<NewsArticle>,
    action: NewsListAction
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = articles,
            key = { it.id }
        ) { article ->
            NewsItemCompose(
                article = article,
                onClick = { action.onArticleClick(article) },
            )
        }
    }
}