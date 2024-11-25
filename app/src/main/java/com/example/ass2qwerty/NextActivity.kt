package com.example.ass2qwerty

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

import com.example.ass2qwerty.data.FilmCollectionsResponse
import com.example.ass2qwerty.Category
import com.example.ass2qwerty.Movie
import okhttp3.OkHttpClient


// --- API Models ---
data class Category(
    val id: String,
    val name: String
)

data class Movie(
    val id: String,
    val title: String,
    val genre: String,
    val imageUrl: String
)

interface ApiService {
    @GET("api/v2.2/collections")
    suspend fun getFilmCollections(): FilmCollectionsResponse

    @GET("api/v2.2/categories")
    suspend fun getCategories(): List<Category>

    @GET("api/v2.2/movies")
    suspend fun getMoviesByCategory(@Query("category") category: String): List<Movie>

    @GET("api/v2.2/films/{collections}")
    suspend fun getCinemas(@retrofit2.http.Path("collections") collections: String): List<Cinema>
    abstract fun getCinemas(): List<Cinema>
}

@Composable

fun FilmCollectionsScreen() {
    var collections by remember { mutableStateOf<FilmCollectionsResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            collections = RetrofitInstance.api.getFilmCollections()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        collections?.sections?.forEach { section ->
            Text(text = section.name)
            LazyRow {
                items(section.films) { film ->
                    Text(text = film.title)
                }
            }
        }
    }
}

// --- Retrofit Instance ---
object RetrofitInstance {
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("X-API-KEY", "473e1748-9874-4151-bc69-4b3644ebb470")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

// --- Main Activity ---
class NextActivity : ComponentActivity() {
    private val items = listOf(
        NavigationItem(R.drawable.home),
        NavigationItem(R.drawable.search),
        NavigationItem(R.drawable.me)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NextScreen(items)
        }
    }
}
@Composable
fun NextActivityScreen() {
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            categories = RetrofitInstance.api.getCategories()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        // ... (rest of the scaffold content)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            // ... (rest of the content)
        }
    }
}

// --- Data Classes ---
data class NavigationItem(val imageRes: Int)

// --- Composables ---
@Composable
fun NextScreen(items: List<NavigationItem>) {
    var selectedItem by remember { mutableStateOf(items.first()) }
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            categories = RetrofitInstance.api.getCategories()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(items, selectedItem) { selectedItem = it }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            Image(
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "Skillcinema",
                modifier = Modifier
                    .size(150.dp, 40.dp)
                    .align(Alignment.TopStart)
                    .offset(x = 16.dp, y = 16.dp)
            )

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = 100.dp)
                ) {
                    items(categories.size) { index ->
                        val category = categories[index]
                        CategorySection(category = category)
                    }

                }
            }
        }
    }
}

@Composable
fun CategorySection(category: Category) {
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(category.id) {
        try {
            movies = RetrofitInstance.api.getMoviesByCategory(category.name)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = category.name,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(movies.size) { index ->
                    val movie = movies[index]
                    MovieBox(movie = movie)
                }

                item {
                    val context = LocalContext.current
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(end = 30.dp)
                            .clickable {
                                val intent = Intent(context, NextNextActivity::class.java).apply {
                                    putExtra("category", category.name)
                                }
                                context.startActivity(intent)
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .padding(bottom = 4.dp),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            "Показать все",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun CinemaListScreen() {
    var cinemas by remember { mutableStateOf<List<Cinema>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            cinemas = RetrofitInstance.api.getCinemas()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        LazyColumn {
            val cinema = 0
            items(cinema) { cinema ->
                Text(text = cinema.dp)
            }
        }
    }
}

private fun LazyItemScope.Text(text: Dp) {
    TODO("Not yet implemented")
}


@Composable
fun MovieBox(movie: Movie) {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .width(100.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = movie.imageUrl),
            contentDescription = movie.title,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(movie.title, fontSize = 14.sp, modifier = Modifier.padding(vertical = 4.dp))
        Text(movie.genre, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    selectedItem: NavigationItem,
    onItemSelected: (NavigationItem) -> Unit
) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterHorizontally)
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = item.imageRes,
                    selected = item == selectedItem,
                    onClick = { onItemSelected(item) }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationItem(icon: Int, selected: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier.size(24.dp)
        )
    }
}
