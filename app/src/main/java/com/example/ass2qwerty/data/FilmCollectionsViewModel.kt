package com.example.ass2qwerty.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ass2qwerty.data.Film
import com.example.ass2qwerty.data.Section
import com.example.ass2qwerty.data.repository.FilmCollectionRepository
import com.example.ass2qwerty.ui.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilmCollectionsViewModel(private val repository: FilmCollectionRepository = FilmCollectionRepository()) : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState<List<Section>>>(ScreenState.Initial)

    private fun <T> MutableStateFlow(any: Any): MutableStateFlow<T> {
        TODO("Not yet implemented")
    }

    class ScreenState<T> {
        companion object {
            fun Success(sectionsList: MutableList<Section>): ScreenState<List<Section>> {
                TODO("Not yet implemented")
            }

            fun Error(s: String): FilmCollectionsViewModel.ScreenState<List<Section>> {


                val Loading: ScreenState<List<Section>> = TODO()
            }

            val Loading: ScreenState<List<Section>> = TODO()

            val Initial: Any = TODO()
        }

    }

    val screenState: StateFlow<ScreenState<List<Section>>> = _screenState

    init {
        loadFilmCollections()
    }

    // Загрузка коллекций фильмов
    fun loadFilmCollections() {
        _screenState.value = ScreenState.Loading
        viewModelScope.launch {
            try {
                val collectionTypes = mapOf(
                    "Top 250 TV Shows" to "TOP_250_TV_SHOWS",
                    "Top 250 Movies" to "TOP_250_MOVIES",
                    "Vampire Theme" to "VAMPIRE_THEME",
                    "Top Popular Movies" to "TOP_POPULAR_MOVIES",
                    "Comics Theme" to "COMICS_THEME"
                )

                val sectionsList = mutableListOf<Section>()

                for ((sectionName, type) in collectionTypes) {
                    val films = mutableListOf<Film>()
                    films.addAll(repository.getFilmCollections(type, 1))
                    films.addAll(repository.getFilmCollections(type, 2))

                    sectionsList.add(Section(sectionName, films))
                }

                _screenState.value = ScreenState.Success(sectionsList)
            } catch (e: Exception) {
                _screenState.value = ScreenState.Error("Failed to load film collections: ${e.message}")
            }
        }
    }
}
