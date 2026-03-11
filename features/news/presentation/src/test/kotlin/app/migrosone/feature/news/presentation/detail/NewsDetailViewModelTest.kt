package app.migrosone.feature.news.presentation.detail

import app.cash.turbine.test
import app.migrosone.contract.AppException
import app.migrosone.contract.ResultState
import app.migrosone.core.presentation.util.IntentUtils
import app.migrosone.feature.news.contract.NewsDetailArgs
import app.migrosone.feature.news.domain.model.NewsArticle
import app.migrosone.feature.news.domain.usecase.GetNewsDetailUseCase
import app.migrosone.feature.news.domain.usecase.IsFavoriteUseCase
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
internal class NewsDetailViewModelTest {

    @MockK
    private lateinit var mockGetNewsDetailUseCase: GetNewsDetailUseCase

    @MockK
    private lateinit var mockToggleFavoriteUseCase: ToggleFavoriteUseCase

    @MockK
    private lateinit var mockIsFavoriteUseCase: IsFavoriteUseCase

    @MockK
    private lateinit var mockNavigation: NewsNavigation

    @MockK
    private lateinit var mockIntentUtils: IntentUtils

    private lateinit var viewModel: NewsDetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val testArticle = NewsArticle(
        id = 1,
        title = "Title",
        summary = "Summary",
        imageUrl = "url",
        publishedAt = "2023-01-01",
        displayDate = "1 Jan 2023",
        url = "http://test.com",
        isFavorite = false
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = NewsDetailViewModel(
            getNewsDetail = mockGetNewsDetailUseCase,
            toggleFavorite = mockToggleFavoriteUseCase,
            isFavorite = mockIsFavoriteUseCase,
            newsNavigation = mockNavigation,
            intentUtils = mockIntentUtils,
        )
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `when initialize is called, state should be updated with article and favorite status`() =
        runTest {
            // Given
            val args = NewsDetailArgs(id = 1)

            every { mockIsFavoriteUseCase.invoke(args.id) } returns flowOf(true)
            every { mockGetNewsDetailUseCase.invoke(args.id) } returns flowOf(ResultState.Success(testArticle))

            // When
            viewModel.initialize(args)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            viewModel.uiState.test {
                val state = awaitItem()
                assert(state is NewsDetailUiState.Success)
                val successState = state as NewsDetailUiState.Success
                assertEquals(true, successState.isFavorite)
                assertEquals(testArticle.title, successState.article.title)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when initialize is called and detail fetch fails, state should be error`() =
        runTest {
            // Given
            val args = NewsDetailArgs(1)
            val errorMessage = "Failed to fetch"
            val appException= AppException.Unknown(cause = Throwable(errorMessage))
            every { mockIsFavoriteUseCase.invoke(args.id) } returns flowOf(false)
            every { mockGetNewsDetailUseCase.invoke(args.id) } returns flowOf(ResultState.Error(appException))

            // When
            viewModel.initialize(args)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            viewModel.uiState.test {
                val state = awaitItem()
                assert(state is NewsDetailUiState.Error)
                assertEquals(errorMessage, (state as NewsDetailUiState.Error).message)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when onBackClick is called, then it navigates up`() = runTest {
        // When
        viewModel.onBackClick()

        // Then
        verify { mockNavigation.navigateUp() }
    }

    @Test
    fun `when onToggleFavorite is called, then use case is invoked`() = runTest {
        // Given
        val args = NewsDetailArgs(1)
        every { mockIsFavoriteUseCase.invoke(args.id) } returns flowOf(false)
        every { mockGetNewsDetailUseCase.invoke(args.id) } returns flowOf(ResultState.Success(testArticle))

        viewModel.initialize(args)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.onToggleFavorite()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockToggleFavoriteUseCase.invoke(testArticle) }
    }

    @Test
    fun `when onShareClick is called, then intentUtils shares text`() = runTest {
        // When
        viewModel.onShareClick("Title", "url")

        // Then
        verify { mockIntentUtils.shareText(any()) }
    }

    @Test
    fun `when onOpenUrl is called, then intentUtils opens browser`() = runTest {
        // When
        viewModel.onOpenUrl("url")

        // Then
        verify { mockIntentUtils.openBrowser("url") }
    }
}
