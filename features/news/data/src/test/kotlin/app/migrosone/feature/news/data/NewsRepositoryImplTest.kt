package app.migrosone.feature.news.data

import app.migrosone.contract.ResultState
import app.migrosone.database.room.room.dao.NewsDao
import app.migrosone.database.room.room.entity.NewsEntity
import app.migrosone.database.room.room.db.AppDatabase
import app.migrosone.feature.news.data.mapper.NewsMapper
import app.migrosone.feature.news.data.remote.NewsApi
import app.migrosone.feature.news.domain.model.NewsArticle
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class NewsRepositoryImplTest {

    @MockK
    private lateinit var mockNewsApi: NewsApi

    @MockK
    private lateinit var mockNewsDao: NewsDao

    @MockK
    private lateinit var mockNewsMapper: NewsMapper

    @MockK
    private lateinit var mockAppDatabase: AppDatabase

    private lateinit var repository: NewsRepositoryImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        repository = NewsRepositoryImpl(
            api = mockNewsApi,
            newsDao = mockNewsDao,
            database = mockAppDatabase,
            mapper = mockNewsMapper
        )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when getFavoriteNews is called, then should return mapped entities`() = runTest {
        // Given
        val entity = NewsEntity(id = 1, title = "Entity")
        val domain = NewsArticle(id = 1, title = "Domain", summary = "", imageUrl = "", publishedAt = "", url = "", isFavorite = true)

        every { mockNewsDao.getAllFavorites() } returns flowOf(listOf(entity))
        every { mockNewsMapper.entityToDomain(entity) } returns domain

        // When
        val result = repository.getFavoriteNews().first()

        // Then
        assertEquals(1, result.size)
        assertEquals("Domain", result.first().title)
    }

    @Test
    fun `given new article, when toggleFavorite is called, then it should insert to database`() = runTest {
        // Given
        val article = NewsArticle(id = 1, title = "Title", summary = "", imageUrl = "", publishedAt = "", url = "", isFavorite = false)
        val entity = NewsEntity(id = 1, title = "Title")

        coEvery { mockNewsDao.getFavoriteById(article.id) } returns null
        every { mockNewsMapper.domainToEntity(article) } returns entity

        // When
        repository.toggleFavorite(article)

        // Then
        coVerify { mockNewsDao.insertFavorite(entity) }
    }

    @Test
    fun `given existing article, when toggleFavorite is called, then it should delete from database`() = runTest {
        // Given
        val article = NewsArticle(id = 1, title = "Title", summary = "", imageUrl = "", publishedAt = "", url = "", isFavorite = true)
        val entity = NewsEntity(id = 1, title = "Title")

        coEvery { mockNewsDao.getFavoriteById(article.id) } returns entity

        // When
        repository.toggleFavorite(article)

        // Then
        coVerify { mockNewsDao.deleteFavorite(entity) }
    }

    @Test
    fun `when isFavorite is called, then should return flow from dao`() = runTest {
        // Given
        every { mockNewsDao.isFavorite(1) } returns flowOf(true)

        // When
        val result = repository.isFavorite(1).first()

        // Then
        assertTrue(result)
    }

    @Test
    fun `when getNewsDetail is called, then it should return Loading and Success`() = runTest {
        // Given
        val domain = NewsArticle(id = 1, title = "Domain", summary = "", imageUrl = "", publishedAt = "", url = "", isFavorite = false)
        val dto = app.migrosone.feature.news.data.remote.model.ArticleDto(
            id = 1, title = "Title", url = "", imageUrl = "", summary = "", publishedAt = "", newsSite = ""
        )

        coEvery { mockNewsApi.getNewsDetail(1) } returns dto
        every { mockNewsMapper.dtoToDomain(dto) } returns domain

        // When
        val result = repository.getNewsDetail(1).toList()

        // Then
        assertEquals(2, result.size)
        assertTrue(result[0] is ResultState.Loading)
        assertTrue(result[1] is ResultState.Success)
        assertEquals("Domain", (result[1] as ResultState.Success).data.title)
    }
}
