package app.migrosone.feature.news.data.remote.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    @SerializedName("results") val results: List<ArticleDto>? = null
)

data class ArticleDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("news_site") val newsSite: String? = null,
    @SerializedName("summary") val summary: String? = null,
    @SerializedName("published_at") val publishedAt: String? = null
)
