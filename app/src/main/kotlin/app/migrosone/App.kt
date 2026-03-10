package app.migrosone

import android.app.Application
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import app.migrosone.uikit.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
internal class App : Application()