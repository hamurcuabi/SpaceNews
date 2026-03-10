package app.migrosone.feature.news.contract

import app.migrosone.navigation.NavigationCommand
import kotlinx.serialization.Serializable

@Serializable
data object NewsListScreenDestination : NavigationCommand.Destination

@Serializable
data class NewsDetailScreenDestination(
    val args: NewsDetailArgs
) : NavigationCommand.Destination

@Serializable
data object FavoritesScreenDestination : NavigationCommand.Destination

@Serializable
data class NewsDetailArgs(
    val id: Int
)
