package app.migrosone.feature.news.domain.model

data class NewsArticle(
    val id: Int,
    val title: String,
    val summary: String,
    val imageUrl: String,
    val publishedAt: String,
    val displayDate: String = "",
    val url: String,
    val isFavorite: Boolean = false
)
