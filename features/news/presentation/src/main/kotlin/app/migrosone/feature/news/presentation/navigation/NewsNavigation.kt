package app.migrosone.feature.news.presentation.navigation

import app.migrosone.feature.news.contract.FavoritesScreenDestination
import app.migrosone.feature.news.contract.NewsDetailArgs
import app.migrosone.feature.news.contract.NewsDetailScreenDestination
import app.migrosone.feature.news.domain.model.NewsArticle
import app.migrosone.navigation.ComposeNavigatorCommand
import app.migrosone.navigation.NavigationManager
import javax.inject.Inject

class NewsNavigation @Inject constructor(
    private var navigationManager: NavigationManager
) {

    fun navigateUp() {
        navigationManager.navigate(navigationCommand = ComposeNavigatorCommand.NavigateUp)
    }

    fun navigateToDetailScreen(article: NewsArticle) {
        navigationManager.navigate(
            navigationCommand = NewsDetailScreenDestination(
                args = NewsDetailArgs(
                    id = article.id
                )
            )
        )
    }

    fun navigateToFavoritesScreen() {
        navigationManager.navigate(navigationCommand = FavoritesScreenDestination)
    }
}
