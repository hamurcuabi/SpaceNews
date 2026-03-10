package app.migrosone.feature.news.presentation.detail

interface NewsDetailAction {
    fun onBackClick()
    fun onToggleFavorite()
    fun onShareClick(title: String, url: String)
    fun onOpenUrl(url: String)
}
