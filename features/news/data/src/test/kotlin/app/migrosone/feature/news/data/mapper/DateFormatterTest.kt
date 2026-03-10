package app.migrosone.feature.news.data.mapper

import app.migrosone.language.ML
import app.migrosone.language.StringResourceManager
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class DateFormatterTest {

    private lateinit var formatter: DateFormatter
    private val resourceManager: StringResourceManager = mockk()

    @BeforeEach
    fun setup() {
        formatter = DateFormatter(resourceManager)
    }

    @Test
    fun `returns empty string when date is null`() {
        val result = formatter.formatToHumanReadable(null)

        assertEquals("", result)
    }

    @Test
    fun `returns empty string when date is blank`() {
        val result = formatter.formatToHumanReadable("")

        assertEquals("", result)
    }

    @Test
    fun `returns just now when less than a minute`() {
        val date = ZonedDateTime.now().minusSeconds(10)
            .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)

        every { resourceManager[ML::justNow] } returns "just now"

        val result = formatter.formatToHumanReadable(date)

        assertEquals("just now", result)
    }

    @Test
    fun `returns minutes ago`() {
        val date = ZonedDateTime.now().minusMinutes(5)
            .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)

        every { resourceManager[ML::minutesAgo, "5"] } returns "5 minutes ago"

        val result = formatter.formatToHumanReadable(date)

        assertEquals("5 minutes ago", result)
    }

    @Test
    fun `returns hour ago`() {
        val date = ZonedDateTime.now().minusHours(1)
            .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)

        every { resourceManager[ML::hourAgo] } returns "1 hour ago"

        val result = formatter.formatToHumanReadable(date)

        assertEquals("1 hour ago", result)
    }

    @Test
    fun `returns hours ago`() {
        val date = ZonedDateTime.now().minusHours(3)
            .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)

        every { resourceManager[ML::hoursAgo, "3"] } returns "3 hours ago"

        val result = formatter.formatToHumanReadable(date)

        assertEquals("3 hours ago", result)
    }

    @Test
    fun `returns yesterday`() {
        val date = ZonedDateTime.now().minusDays(1)
            .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)

        every { resourceManager[ML::yesterday] } returns "yesterday"

        val result = formatter.formatToHumanReadable(date)

        assertEquals("yesterday", result)
    }

    @Test
    fun `returns days ago`() {
        val date = ZonedDateTime.now().minusDays(3)
            .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)

        every { resourceManager[ML::daysAgo, "3"] } returns "3 days ago"

        val result = formatter.formatToHumanReadable(date)

        assertEquals("3 days ago", result)
    }

    @Test
    fun `returns formatted date when older than a week`() {
        val dateTime = ZonedDateTime.now().minusDays(10)
        val date = dateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)

        val result = formatter.formatToHumanReadable(date)

        val expected = dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))

        assertEquals(expected, result)
    }

    @Test
    fun `returns original string when parsing fails`() {
        val invalidDate = "invalid-date"

        val result = formatter.formatToHumanReadable(invalidDate)

        assertEquals(invalidDate, result)
    }
}