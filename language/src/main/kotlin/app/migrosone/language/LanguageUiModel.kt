package app.migrosone.language

import app.migrosone.language.resources.LanguageType

internal data class LanguageUiModel(
    val language: String = LanguageType.EN.name,
    val resources: StringResourcesUiModel = StringResourcesUiModel()
)
