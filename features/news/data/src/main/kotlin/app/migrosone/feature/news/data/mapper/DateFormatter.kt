package app.migrosone.feature.news.data.mapper

import app.migrosone.language.ML
import app.migrosone.language.StringResourceManager
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class DateFormatter @Inject constructor(
    private val resourceManager: StringResourceManager
) {
    private val isoFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
    private val displayFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())

    fun formatToHumanReadable(dateString: String?): String {
        if (dateString.isNullOrBlank()) return ""

        return try {
            val dateTime = ZonedDateTime.parse(dateString, isoFormatter)
            val now = ZonedDateTime.now()
            val duration = Duration.between(dateTime, now)
            val days = duration.toDays()
            val hours = duration.toHours()
            val minutes = duration.toMinutes()

            when {
                minutes < 1 -> resourceManager[ML::justNow]
                minutes < 60 -> resourceManager[ML::minutesAgo, minutes.toString()]
                hours < 24 -> {
                    if (hours == 1L) resourceManager[ML::hourAgo]
                    else resourceManager[ML::hoursAgo, hours.toString()]
                }
                days == 1L -> resourceManager[ML::yesterday]
                days < 7 -> resourceManager[ML::daysAgo, days.toString()]
                else -> dateTime.format(displayFormatter)
            }
        } catch (e: Exception) {
            dateString
        }
    }
}