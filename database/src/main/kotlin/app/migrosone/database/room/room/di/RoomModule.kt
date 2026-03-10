package app.migrosone.database.room.room.di

import android.content.Context
import androidx.room.Room
import app.migrosone.database.room.room.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DB_NAME_NB = "migros"

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME_NB)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    @Singleton
    fun provideNewsDao(db: AppDatabase) = db.newsDao()

    @Provides
    @Singleton
    fun provideCachedNewsDao(db: AppDatabase) = db.cachedNewsDao()
}
