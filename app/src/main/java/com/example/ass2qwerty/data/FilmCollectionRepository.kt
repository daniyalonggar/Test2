package com.example.ass2qwerty.data.repository

import com.example.ass2qwerty.data.Film
import com.example.ass2qwerty.data.FilmCollectionsResponse
import com.example.ass2qwerty.data.KinopoiskCollections
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmCollectionRepository {

    private val apiService = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")  // Replace with the correct API base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(KinopoiskCollections::class.java)

    suspend fun getFilmCollections(type: String, page: Int): List<Film> {
        val response = apiService.getFilmCollections(type, page)
        return response.films
    }

}

