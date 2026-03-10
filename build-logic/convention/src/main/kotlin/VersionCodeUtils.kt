@file:Suppress("MissingPackageDeclaration")

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

object VersionCodeUtils {

    private val formatter = SimpleDateFormat("MMddHHmmss").apply {
        timeZone = TimeZone.getTimeZone("Europe/Istanbul")
    }

    val buildVersionCode: Int
        get() = formatter.format(Calendar.getInstance().time).toInt()
}
