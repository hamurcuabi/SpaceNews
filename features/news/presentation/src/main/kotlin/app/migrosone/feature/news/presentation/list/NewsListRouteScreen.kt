package app.migrosone.feature.news.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun NewsListRouteScreen(viewModel: NewsListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    NewsListScreen(
        searchQuery = searchQuery,
        state = uiState,
        action = viewModel
    )
}
