package app.migrosone.core.data.dispatchers

import app.migrosone.contract.Dispatchers.Default
import app.migrosone.contract.Dispatchers.IO
import app.migrosone.contract.Dispatchers.Main
import app.migrosone.contract.Dispatchers.Unconfined
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal object DispatchersModule {

    @Provides
    @IO
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Main
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Default
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Unconfined
    fun provideUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}
