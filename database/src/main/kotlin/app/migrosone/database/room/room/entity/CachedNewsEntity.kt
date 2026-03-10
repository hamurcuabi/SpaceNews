package app.migrosone.database.room.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_news")
data class CachedNewsEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val summary: String,
    val imageUrl: String,
    val publishedAt: String,
    val url: String
)
