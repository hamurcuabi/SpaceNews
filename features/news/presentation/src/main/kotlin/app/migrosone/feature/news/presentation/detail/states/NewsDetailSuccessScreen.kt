package app.migrosone.feature.news.presentation.detail.states

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.migrosone.feature.news.domain.model.NewsArticle
import app.migrosone.feature.news.presentation.detail.NewsDetailAction
import app.migrosone.language.ML
import app.migrosone.language.StringResourceManager
import app.migrosone.uikit.CustomColorsPalette
import app.migrosone.uikit.components.image.KtAsyncImage
import app.migrosone.uikit.components.text.KtText

@Composable
internal fun NewsDetailSuccessScreen(
    article: NewsArticle,
    color: CustomColorsPalette,
    newsAction: NewsDetailAction,
    stringResourceManager: StringResourceManager
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color.white)
    ) {
        KtAsyncImage(
            image = article.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(16.dp)) {
            KtText(
                text = article.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color.textBlack
            )

            Spacer(modifier = Modifier.height(8.dp))

            KtText(
                text = article.displayDate,
                fontSize = 12.sp,
                color = color.textGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = color.backgroundColor, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            KtText(
                text = article.summary,
                fontSize = 16.sp,
                color = color.textBlack,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    newsAction.onOpenUrl(article.url)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = color.primaryColor)
            ) {
                Text(stringResourceManager[ML::readFullArticleButton])
            }
        }
    }
}