package app.migrosone.feature.news.data.remote

import app.migrosone.feature.news.data.remote.model.ArticleDto
import app.migrosone.feature.news.data.remote.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {

    @GET("articles/")
    suspend fun getNews(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("search") search: String? = null
    ): NewsResponse

    @GET("articles/{id}/")
    suspend fun getNewsDetail(@Path("id") id: Int): ArticleDto
}
