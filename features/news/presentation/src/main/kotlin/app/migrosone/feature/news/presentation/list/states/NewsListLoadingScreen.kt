package app.migrosone.feature.news.presentation.list.states

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.migrosone.feature.news.presentation.component.NewsItemShimmer

@Composable
internal fun NewsListLoadingScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(5) {
            NewsItemShimmer()
        }
    }
}