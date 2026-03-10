package app.migrosone.feature.news.data.mapper

import app.migrosone.database.room.room.entity.CachedNewsEntity
import app.migrosone.database.room.room.entity.NewsEntity
import app.migrosone.feature.news.data.remote.model.ArticleDto
import app.migrosone.feature.news.domain.model.NewsArticle
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class NewsMapperTest {

    private lateinit var mapper: NewsMapper
    private val dateFormatter: DateFormatter = mockk()

    @BeforeEach
    fun setup() {
        mapper = NewsMapper(dateFormatter)
    }

    @Test
    fun `dtoToDomain should map dto to domain`() {
        val dto = ArticleDto(
            id = 1,
            title = "Title",
            summary = "Summary",
            imageUrl = "image.jpg",
            publishedAt = "2026-03-10T10:00:00Z",
            url = "https://news.com"
        )

        every { dateFormatter.formatToHumanReadable(dto.publishedAt) } returns "10 Mar 2026"

        val result = mapper.dtoToDomain(dto)

        assertEquals(1, result.id)
        assertEquals("Title", result.title)
        assertEquals("Summary", result.summary)
        assertEquals("image.jpg", result.imageUrl)
        assertEquals("2026-03-10T10:00:00Z", result.publishedAt)
        assertEquals("10 Mar 2026", result.displayDate)
        assertEquals("https://news.com", result.url)
    }

    @Test
    fun `entityToDomain should map entity to domain and set favorite`() {
        val entity = NewsEntity(
            id = 2,
            title = "Title",
            summary = "Summary",
            imageUrl = "image.jpg",
            publishedAt = "2026-03-10",
            url = "https://news.com"
        )

        every { dateFormatter.formatToHumanReadable(entity.publishedAt) } returns "10 Mar 2026"

        val result = mapper.entityToDomain(entity)

        assertEquals(2, result.id)
        assertEquals(true, result.isFavorite)
        assertEquals("10 Mar 2026", result.displayDate)
    }

    @Test
    fun `domainToEntity should map domain to entity`() {
        val article = NewsArticle(
            id = 3,
            title = "Title",
            summary = "Summary",
            imageUrl = "image.jpg",
            publishedAt = "2026-03-10",
            displayDate = "10 Mar 2026",
            url = "https://news.com",
            isFavorite = false
        )

        val result = mapper.domainToEntity(article)

        assertEquals(3, result.id)
        assertEquals("Title", result.title)
        assertEquals("Summary", result.summary)
        assertEquals("image.jpg", result.imageUrl)
        assertEquals("2026-03-10", result.publishedAt)
        assertEquals("https://news.com", result.url)
    }

    @Test
    fun `cachedToDomain should map cached entity to domain`() {
        val entity = CachedNewsEntity(
            id = 4,
            title = "Title",
            summary = "Summary",
            imageUrl = "image.jpg",
            publishedAt = "2026-03-10",
            url = "https://news.com"
        )

        every { dateFormatter.formatToHumanReadable(entity.publishedAt) } returns "10 Mar 2026"

        val result = mapper.cachedToDomain(entity)

        assertEquals(4, result.id)
        assertEquals("Title", result.title)
        assertEquals("10 Mar 2026", result.displayDate)
    }

    @Test
    fun `domainToCached should map domain to cached entity`() {
        val article = NewsArticle(
            id = 5,
            title = "Title",
            summary = "Summary",
            imageUrl = "image.jpg",
            publishedAt = "2026-03-10",
            displayDate = "10 Mar 2026",
            url = "https://news.com",
            isFavorite = false
        )

        val result = mapper.domainToCached(article)

        assertEquals(5, result.id)
        assertEquals("Title", result.title)
        assertEquals("Summary", result.summary)
        assertEquals("image.jpg", result.imageUrl)
        assertEquals("2026-03-10", result.publishedAt)
        assertEquals("https://news.com", result.url)
    }
}