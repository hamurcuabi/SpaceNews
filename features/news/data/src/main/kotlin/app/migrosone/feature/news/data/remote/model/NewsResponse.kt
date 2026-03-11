package app.migrosone.feature.news.data.remote.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("count") val count: Int = 0,
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    @SerializedName("results") val results: List<ArticleDto> = emptyList()
)

data class ArticleDto(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("title") val title: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("image_url") val imageUrl: String = "",
    @SerializedName("news_site") val newsSite: String = "",
    @SerializedName("summary") val summary: String = "",
    @SerializedName("published_at") val publishedAt: String = ""
)
