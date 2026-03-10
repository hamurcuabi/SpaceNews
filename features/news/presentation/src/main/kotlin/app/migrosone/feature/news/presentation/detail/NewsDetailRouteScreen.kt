package app.migrosone.feature.news.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.migrosone.feature.news.contract.NewsDetailArgs

@Composable
fun NewsDetailRouteScreen(
    args: NewsDetailArgs,
    viewModel: NewsDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = args) {
        viewModel.initialize(args)
    }

    NewsDetailScreen(
        state = uiState,
        newsAction = viewModel
    )
}
