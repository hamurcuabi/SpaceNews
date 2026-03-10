package app.migrosone.feature.news.presentation.detail

import androidx.compose.runtime.Stable

@Stable
interface NewsDetailAction {
    fun onBackClick()
    fun onToggleFavorite()
    fun onShareClick(title: String, url: String)
    fun onOpenUrl(url: String)
}
