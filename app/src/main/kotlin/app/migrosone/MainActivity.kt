package app.migrosone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation3.runtime.rememberNavBackStack
import app.migrosone.feature.news.contract.NewsListScreenDestination
import app.migrosone.language.StringResourceManager
import app.migrosone.navigation.NavGraphProvider
import app.migrosone.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var navGraphProviders: Map<String, @JvmSuppressWildcards NavGraphProvider>

    @Inject
    lateinit var stringResourceManager: StringResourceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stringResourceManager.setLanguage(Locale.getDefault().language)

        enableEdgeToEdge()

        setContent {
            val backStack = rememberNavBackStack(NewsListScreenDestination)

            AppRouteScreen(
                backStack = backStack,
                navGraphProviders = navGraphProviders,
                stringResourceManager = stringResourceManager
            )

            NavigationLaunchedEffect(
                navigationManager = navigationManager,
                backStack = backStack
            )
        }
    }
}
