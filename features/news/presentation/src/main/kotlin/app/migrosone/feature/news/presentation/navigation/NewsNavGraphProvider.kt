package app.migrosone.feature.news.presentation.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import app.migrosone.feature.news.contract.FavoritesScreenDestination
import app.migrosone.feature.news.contract.NewsDetailScreenDestination
import app.migrosone.feature.news.contract.NewsListScreenDestination
import app.migrosone.feature.news.presentation.detail.NewsDetailRouteScreen
import app.migrosone.feature.news.presentation.favorites.FavoritesRouteScreen
import app.migrosone.feature.news.presentation.list.NewsListRouteScreen
import app.migrosone.navigation.NavGraphProvider
import javax.inject.Inject

internal class NewsNavGraphProvider @Inject constructor() : NavGraphProvider {
    override fun registerGraph(provider: EntryProviderBuilder<NavKey>) {
        provider.entry<NewsListScreenDestination> {
            NewsListRouteScreen()
        }

        provider.entry<NewsDetailScreenDestination> { key ->
            NewsDetailRouteScreen(args = key.args)
        }
        
        provider.entry<FavoritesScreenDestination> {
            FavoritesRouteScreen()
        }
    }
}
