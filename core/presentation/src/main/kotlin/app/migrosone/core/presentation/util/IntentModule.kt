package app.migrosone.core.presentation.util

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityRetainedComponent::class)
object IntentModule {

    @Provides
    fun provideIntentUtils(@ApplicationContext context: Context): IntentUtils {
        return IntentUtils(context)
    }
}