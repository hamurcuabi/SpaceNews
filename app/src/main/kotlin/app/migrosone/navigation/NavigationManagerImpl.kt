package app.migrosone.navigation

import app.migrosone.contract.Dispatchers
import app.migrosone.navigation.NavigationCommand
import app.migrosone.navigation.NavigationManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal class NavigationManagerImpl @Inject constructor(
    @Dispatchers.Main dispatcher: CoroutineDispatcher
) : CoroutineScope, NavigationManager {

    override val coroutineContext: CoroutineContext = dispatcher + Job()

    private val navigationCommandChannel = Channel<NavigationCommand>()
    override val navigationCommandFlow: Flow<NavigationCommand>
        get() = navigationCommandChannel.receiveAsFlow()

    override fun navigate(navigationCommand: NavigationCommand) {
        launch {
            navigationCommandChannel.send(navigationCommand)
        }
    }
}
