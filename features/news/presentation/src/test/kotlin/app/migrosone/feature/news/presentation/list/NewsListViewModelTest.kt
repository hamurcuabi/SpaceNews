package app.migrosone.feature.news.presentation.list

import app.cash.turbine.test
import app.migrosone.contract.ResultState
import app.migrosone.feature.news.domain.model.NewsArticle
import app.migrosone.feature.news.domain.usecase.GetNewsUseCase
import app.migrosone.feature.news.domain.usecase.ToggleFavoriteUseCase
import app.migrosone.feature.news.presentation.navigation.NewsNavigation
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class NewsListViewModelTest {

    @MockK
    private lateinit var mockGetNewsUseCase: GetNewsUseCase

    @MockK
    private lateinit var mockNavigation: NewsNavigation

    private lateinit var viewModel: NewsListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)

        every {
            mockGetNewsUseCase.invoke(any())
        } returns flowOf(ResultState.Success(emptyList()))

        viewModel = NewsListViewModel(
            getNews = mockGetNewsUseCase,
            newsNavigation = mockNavigation
        )
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `when onSearchQueryChanged is called, then searchQuery should update`() = runTest {
        // Given
        val query = "SpaceX"

        // When
        viewModel.onSearchQueryChanged(query)

        // Then
        viewModel.searchQuery.test {
            assertEquals(query, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when onArticleClick is called, then navigation should be triggered with article`() = runTest {
        // Given
        val article = NewsArticle(
            id = 1,
            title = "Test Title",
            summary = "Test Summary",
            imageUrl = "url",
            publishedAt = "2023-01-01",
            displayDate = "1 Jan 2023",
            url = "http://test.com",
            isFavorite = false
        )

        // When
        viewModel.onArticleClick(article)

        // Then
        verify { mockNavigation.navigateToDetailScreen(article) }
    }

    @Test
    fun `when news use case returns success, then state articles should be updated`() = runTest {
        // Given
        val articles = listOf(
            NewsArticle(1, "Title", "Summary", "url", "2023", "1 Jan 2023", "url", false)
        )
        every { mockGetNewsUseCase.invoke(any()) } returns flowOf(ResultState.Success(articles))

        // When subscribing to uiState
        viewModel.uiState.test {
            testDispatcher.scheduler.advanceUntilIdle()
            // Skip initial Loading
            val state = expectMostRecentItem()
            assert(state is NewsListUiState.Success)
            assertEquals(articles, (state as NewsListUiState.Success).articles)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when news use case returns error, then state error should be updated`() = runTest {
        // Given
        val errorMessage = "Error message"
        every { mockGetNewsUseCase.invoke(any()) } returns flowOf(ResultState.Error(errorMessage,-1))

        // When subscribing to uiState
        viewModel.uiState.test {
            testDispatcher.scheduler.advanceUntilIdle()
            val state = expectMostRecentItem()
            assert(state is NewsListUiState.Error)
            assertEquals(errorMessage, (state as NewsListUiState.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when navigateToFavorites is called, then navigation to favorites should be triggered`() = runTest {
        // When
        viewModel.navigateToFavorites()

        // Then
        verify { mockNavigation.navigateToFavoritesScreen() }
    }
}
