package com.example.ass2qwerty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import android.content.Intent


data class Cinema(val name: String, val genre: String, val imageUrl: String)

interface ApiService {
    @GET("api/v2.2/films/{collections}") // Укажите свой endpoint
    suspend fun getCinemas(): List<Cinema>
}

@OptIn(ExperimentalMaterial3Api::class)
class NextNextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val category = intent.getStringExtra("category") ?: "Unknown"
        setContent {
            CinemaListScreen(category)
        }
    }
}

fun createRetrofit(): Retrofit {
    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-API-KEY", "473e1748-9874-4151-bc69-4b3644ebb470")
                .build()
            chain.proceed(request)
        }
        .build()

    return Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

@Composable
fun CinemaListScreen(category: String) {
    Scaffold(
        topBar = { TopAppBarWithBackButton() },
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        CinemaGrid(modifier = Modifier.padding(paddingValues))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBackButton() {
    val context = LocalContext.current

    TopAppBar(
        title = { Text(text = "Кинотека") },
        navigationIcon = {
            IconButton(onClick = {
                // Запуск Intent для перехода на NextActivity
                context.startActivity(Intent(context, NextActivity::class.java))
            }) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Назад"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}



@Composable
fun CinemaGrid(modifier: Modifier = Modifier) {
    var cinemaList by remember { mutableStateOf(emptyList<Cinema>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val retrofit = createRetrofit()
        val service = retrofit.create(ApiService::class.java)
        try {
            cinemaList = service.getCinemas()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(cinemaList) { cinema ->
                MovieBox(cinema)
            }
        }
    }
}

@Composable
fun MovieBox(cinema: Cinema) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = rememberImagePainter(data = cinema.imageUrl),
            contentDescription = cinema.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(text = cinema.name, fontSize = 14.sp, modifier = Modifier.padding(vertical = 4.dp))
        Text(text = cinema.genre, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White),
        containerColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterHorizontally)
        ) {
            BottomNavigationItem(icon = R.drawable.home, onClick = { /* TODO: Action */ })
            BottomNavigationItem(icon = R.drawable.search, onClick = { /* TODO: Action */ })
            BottomNavigationItem(icon = R.drawable.me, onClick = { /* TODO: Action */ })
        }
    }
}

@Composable
fun BottomNavigationItem(icon: Int, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null
        )
    }
}
