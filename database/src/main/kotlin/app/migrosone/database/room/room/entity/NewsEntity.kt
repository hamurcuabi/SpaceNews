package app.migrosone.database.room.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "summary")
    val summary: String = "",
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String = "",
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String = "",
    @ColumnInfo(name = "url")
    val url: String = ""
)
