package app.migrosone.language.resources

import app.migrosone.language.LanguageUiModel
import app.migrosone.language.StringResourcesUiModel

internal val resourceEN = LanguageUiModel(
    language = LanguageType.EN.code,
    resources = StringResourcesUiModel(
        errorOccurredTitle = "An error occurred: {0}",
        retryButtonTitle = "Retry",
        newsListTitle = "Space News",
        newsSearchPlaceholder = "Search space discoveries...",
        newsErrorTitle = "Houston, we have a problem",
        newsListEmpty = "No space news found",
        favoriteListTitle = "Favorite Articles",
        favoriteListEmpty = "No favorite articles yet.",
        newsDetailTitle = "Article Details",
        readFullArticleButton = "Read Full Article",
        shareButtonDescription = "Share",
        favoriteButtonDescription = "Favorite",
        backButtonDescription = "Back",
        justNow = "Just now",
        minutesAgo = "{0} min ago",
        hourAgo = "1 hour ago",
        hoursAgo = "{0} hours ago",
        yesterday = "Yesterday",
        daysAgo = "{0} days ago"
    )
)
