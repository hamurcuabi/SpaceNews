package app.migrosone

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import app.migrosone.language.LocalStringResourceManager
import app.migrosone.language.StringResourceManager
import app.migrosone.navigation.NavGraphProvider
import app.migrosone.uikit.LocalCustomColorsPalette
import app.migrosone.uikit.OnDarkCustomColorsPalette
import app.migrosone.uikit.OnLightCustomColorsPalette

@Composable
internal fun AppRouteScreen(
    backStack: NavBackStack,
    stringResourceManager: StringResourceManager,
    navGraphProviders: Map<String, NavGraphProvider>
) {
    val isSystemDark =
        if (isSystemInDarkTheme()) OnDarkCustomColorsPalette else OnLightCustomColorsPalette

    CompositionLocalProvider(value = LocalCustomColorsPalette provides isSystemDark) {
        CompositionLocalProvider(value = LocalStringResourceManager provides stringResourceManager) {
            val color = LocalCustomColorsPalette.current

            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color.primaryColor)
                        .height(
                            height = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                        )
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .background(color.backgroundColor)
                ) {
                    NavDisplay(
                        backStack = backStack,
                        transitionSpec = {
                            slideInHorizontally(initialOffsetX = { it }) togetherWith
                                    slideOutHorizontally(targetOffsetX = { -it })
                        },
                        popTransitionSpec = {
                            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                    slideOutHorizontally(targetOffsetX = { it })
                        },
                        predictivePopTransitionSpec = {
                            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                    slideOutHorizontally(targetOffsetX = { it })
                        },
                        entryProvider = entryProvider {
                            navGraphProviders.values.forEach {
                                it.registerGraph(this)
                            }
                        }
                    )
                }
            }
        }
    }
}
