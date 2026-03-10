package app.migrosone.feature.news.presentation.di

import app.migrosone.feature.news.presentation.navigation.NewsNavGraphProvider
import app.migrosone.navigation.NavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface CartNavGraphModule {

    @Binds
    @IntoMap
    @StringKey("news")
    fun binds(
        newsNavGraphProvider: NewsNavGraphProvider
    ): NavGraphProvider
}
