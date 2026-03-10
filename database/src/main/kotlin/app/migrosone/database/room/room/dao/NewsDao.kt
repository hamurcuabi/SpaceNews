package app.migrosone.database.room.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.migrosone.database.room.room.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM NewsEntity")
    fun getAllFavorites(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(news: NewsEntity)

    @Delete
    suspend fun deleteFavorite(news: NewsEntity)

    @Query("SELECT EXISTS(SELECT * FROM NewsEntity WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>

    @Query("SELECT * FROM NewsEntity WHERE id = :id")
    suspend fun getFavoriteById(id: Int): NewsEntity?

    @Query("SELECT id FROM NewsEntity")
    fun getAllFavoriteIds(): Flow<List<Int>>
}
