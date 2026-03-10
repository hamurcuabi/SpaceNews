package app.migrosone.database.room.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.migrosone.database.room.room.entity.CachedNewsEntity

@Dao
interface CachedNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<CachedNewsEntity>)

    @Query("SELECT * FROM cached_news ORDER BY publishedAt DESC")
    suspend fun getAllCachedNewsList(): List<CachedNewsEntity>

    @Query("SELECT * FROM cached_news WHERE id = :id")
    suspend fun getCachedNewsById(id: Int): CachedNewsEntity?

    @Query("DELETE FROM cached_news")
    suspend fun clearAll()
}
