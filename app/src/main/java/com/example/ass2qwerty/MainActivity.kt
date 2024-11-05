package com.example.ass2qwerty
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import android.content.Intent


data class OnboardingPage(
    val imageRes: Int,
    val text: String,
    val dotImages: List<Int>
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnboardingScreens {
                startActivity(Intent(this, NextActivity::class.java))
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreens(onSkipClicked: () -> Unit) {

    val onboardingPages = listOf(
        OnboardingPage(R.drawable.photo1, "Узнавай о премьерах", listOf(R.drawable.blackdot, R.drawable.whitedot, R.drawable.whitedot)),
        OnboardingPage(R.drawable.photo2, "Создавай коллекции", listOf(R.drawable.whitedot, R.drawable.blackdot, R.drawable.whitedot)),
        OnboardingPage(R.drawable.photo3, "Делись с друзьями", listOf(R.drawable.whitedot, R.drawable.whitedot, R.drawable.blackdot))
    )

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp, 18.24.dp)
                    .padding(start = 16.dp)
            )

            TextButton(
                onClick = { onSkipClicked() },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(
                    text = "Пропустить",
                    color = Color(0xFFB5B5C9),
                    fontSize = 15.sp
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            count = onboardingPages.size,
            modifier = Modifier
                .weight(1f)
        ) { page ->
            OnboardingScreen(
                imageRes = onboardingPages[page].imageRes,
                text = onboardingPages[page].text,
                dotImages = onboardingPages[page].dotImages
            )
        }
    }
}

@Composable
fun OnboardingScreen(imageRes: Int, text: String, dotImages: List<Int>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),

        )

        Text(
            text = text,
            fontSize = 40.sp,
            modifier = Modifier
                .padding(vertical = 50.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Left
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            dotImages.forEach { dotResId ->
                Image(
                    painter = painterResource(id = dotResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OnboardingScreens(onSkipClicked = {})
}
