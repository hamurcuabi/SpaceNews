package app.migrosone.database.room.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.migrosone.database.room.room.dao.CachedNewsDao
import app.migrosone.database.room.room.dao.NewsDao
import app.migrosone.database.room.room.entity.CachedNewsEntity
import app.migrosone.database.room.room.entity.NewsEntity

@Database(
    entities = [
        NewsEntity::class,
        CachedNewsEntity::class
    ],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun cachedNewsDao(): CachedNewsDao
}
