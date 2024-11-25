package com.example.ass2qwerty.data

import retrofit2.http.GET
import retrofit2.http.Query

interface KinopoiskCollections {
    @GET("api/v2.2/films/collections")
    suspend fun getFilmCollections(
        @Query("type") type: String,
        @Query("page") page: Int
    ): FilmCollectionsResponse
}
