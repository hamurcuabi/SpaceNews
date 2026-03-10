package app.migrosone.language.di

import app.migrosone.language.LanguageUiModel
import app.migrosone.language.StringResourceManager
import app.migrosone.language.StringResourcesUiModel
import app.migrosone.language.resources.resourceEN
import app.migrosone.language.resources.stringResources
import java.util.Locale
import javax.inject.Inject
import kotlin.reflect.KProperty1

internal class StringResourceManagerImpl @Inject constructor() : StringResourceManager {
    
    private var currentResource = stringResources.find { it.language == Locale.getDefault().language }
        ?: resourceEN

    override fun setLanguage(code: String) {
        currentResource = stringResources.find { it.language == Locale.getDefault().language }
            ?: resourceEN
    }

    override fun get(property: KProperty1<StringResourcesUiModel, String>): String {
        return property.get(currentResource.resources)
    }

    override fun get(
        property: KProperty1<StringResourcesUiModel, String>,
        vararg params: String
    ): String {
        var resource = property.get(currentResource.resources)
        params.forEachIndexed { index, param ->
            resource = resource.replace("{$index}", param)
        }
        return resource
    }
}
