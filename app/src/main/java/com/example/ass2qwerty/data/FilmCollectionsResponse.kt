package com.example.ass2qwerty.data
import com.google.gson.annotations.SerializedName
data class FilmCollectionsResponse(
    val sections: List<Section>
) {
    val films: List<Film> = TODO()

}


data class Section(
    val name: String,
    val films: List<Film>
)



data class Film(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("posterUrl") val posterUrl: String,
    @SerializedName("genre") val genre: String
)

data class Cinema(
    val id: String,
    val name: String,
    val address: String,
    val imageUrl: String
)