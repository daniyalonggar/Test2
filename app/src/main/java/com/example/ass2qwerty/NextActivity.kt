package com.example.ass2qwerty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape



data class NavigationItem( val imageRes: Int)
data class Movie(val title: String, val genre: String, val imageRes: Int)

class NextActivity : ComponentActivity() {
    private val items = listOf(
        NavigationItem( R.drawable.home),
        NavigationItem( R.drawable.search),
        NavigationItem( R.drawable.me)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NextScreen(items)
        }
    }
}

@Composable
fun NextScreen(items: List<NavigationItem>) {
    var selectedItem by remember { mutableStateOf(items.first()) }

    Scaffold(
        bottomBar = {
                BottomNavigationBar(items, selectedItem) { selectedItem = it }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(bottom = innerPadding.calculateBottomPadding())) {
            Image(
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "Skillcinema",
               modifier = Modifier
                    .size(150.dp, 40.dp)
                    .align(Alignment.TopStart)
                    .offset(x = 16.dp, y = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 100.dp)
            ) {
                val movieCategories = listOf(
                    "Премьеры" to listOf(
                        Movie("Movie 1", "Action", R.drawable.kino),
                        Movie("Movie 2", "Action", R.drawable.kino),
                        Movie("Movie 3", "Action", R.drawable.kino),
                        Movie("Movie 4", "Action", R.drawable.kino),
                        Movie("Movie 5", "Action", R.drawable.kino),
                        Movie("Movie 6", "Action", R.drawable.kino),
                        Movie("Movie 7", "Action", R.drawable.kino),
                        Movie("Movie 8", "Action", R.drawable.kino)
                    ),
                    "Популярное" to listOf(
                        Movie("Movie 1", "Drama", R.drawable.kino),
                        Movie("Movie 2", "Drama", R.drawable.kino),
                        Movie("Movie 3", "Drama", R.drawable.kino),
                        Movie("Movie 4", "Drama", R.drawable.kino),
                        Movie("Movie 5", "Drama", R.drawable.kino),
                        Movie("Movie 6", "Drama", R.drawable.kino),
                        Movie("Movie 7", "Drama", R.drawable.kino),
                        Movie("Movie 8", "Drama", R.drawable.kino)
                    ),

                    "Боевики США" to listOf(
                        Movie("Movie 1", "Drama", R.drawable.kino),
                        Movie("Movie 2", "Drama", R.drawable.kino),
                        Movie("Movie 3", "Drama", R.drawable.kino),
                        Movie("Movie 4", "Drama", R.drawable.kino),
                        Movie("Movie 5", "Drama", R.drawable.kino),
                        Movie("Movie 6", "Drama", R.drawable.kino),
                        Movie("Movie 7", "Drama", R.drawable.kino),
                        Movie("Movie 8", "Drama", R.drawable.kino)
                    ),
                    "ТОП-250" to listOf(
                        Movie("Movie 1", "Drama", R.drawable.kino),
                        Movie("Movie 2", "Drama", R.drawable.kino),
                        Movie("Movie 3", "Drama", R.drawable.kino),
                        Movie("Movie 4", "Drama", R.drawable.kino),
                        Movie("Movie 5", "Drama", R.drawable.kino),
                        Movie("Movie 6", "Drama", R.drawable.kino),
                        Movie("Movie 7", "Drama", R.drawable.kino),
                        Movie("Movie 8", "Drama", R.drawable.kino)
                    ),
                    "Топ рейтинга" to listOf(
                        Movie("Movie 1", "Drama", R.drawable.kino),
                        Movie("Movie 2", "Drama", R.drawable.kino),
                        Movie("Movie 3", "Drama", R.drawable.kino),
                        Movie("Movie 4", "Drama", R.drawable.kino),
                        Movie("Movie 5", "Drama", R.drawable.kino),
                        Movie("Movie 6", "Drama", R.drawable.kino),
                        Movie("Movie 7", "Drama", R.drawable.kino),
                        Movie("Movie 8", "Drama", R.drawable.kino)
                    ),
                    "Топ рейтингафф" to listOf(
                        Movie("Movie 1", "Drama", R.drawable.kino),
                        Movie("Movie 2", "Drama", R.drawable.kino),
                        Movie("Movie 3", "Drama", R.drawable.kino),
                        Movie("Movie 4", "Drama", R.drawable.kino),
                        Movie("Movie 5", "Drama", R.drawable.kino),
                        Movie("Movie 6", "Drama", R.drawable.kino),
                        Movie("Movie 7", "Drama", R.drawable.kino),
                        Movie("Movie 8", "Drama", R.drawable.kino)
                    ),
                )

                items(movieCategories) { (category, movies) ->
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(text = category, fontSize = 20.sp, modifier = Modifier.padding(start = 16.dp, bottom = 4.dp))

                        LazyRow(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(movies) { movie ->
                                MovieBox(movie)

                            }
                            item {
                                Column(
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.Center,
                                            modifier = Modifier.padding(end = 30.dp)
                                ) {

                                        Image(
                                            painter = painterResource(id = R.drawable.icon),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(bottom = 4.dp)
                                            .align(Alignment.Start),
                                            contentScale = ContentScale.Crop
                                        )

                                    Text("Показать все",  fontSize = 12.sp, modifier = Modifier.padding(top = 10.dp).align(Alignment.End))
                                }

                            }
                        }


                    }
                }
            }
        }
    }
}


@Composable
fun MovieBox(movie: Movie) {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .width(100.dp)
    ) {
        Image(
            painter = painterResource(id = movie.imageRes),
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
    onItemSelected: (NavigationItem) -> Unit) {

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        items = items

    )

}

@Composable
fun BottomNavigationItem(icon: Int, selected: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = ""

        )
    }
}

@Composable
fun BottomNavigation(items: List<NavigationItem>, backgroundColor: Color, contentColor: Color ){
    BottomAppBar (
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            ).background(backgroundColor)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterHorizontally)
        ){
            items.forEach { item ->
                BottomNavigationItem(
                    icon = item.imageRes,
                    selected = true,
                    onClick = {  }
                )
            }
        }
    }
}


