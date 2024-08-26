package com.example.galleryapp.popular_fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.api.RetrofitClient
import com.example.galleryapp.data.ResultCharacter
import com.example.galleryapp.new_fragment.NewFragmentUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PopularFragmentUiState(
    val pictureList: List<ResultCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

class PopularViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PopularFragmentUiState())
    val uiState = _uiState.asStateFlow()

    fun getPicture() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, isError = false)

                val response = RetrofitClient.api.getDeadCharacters().execute()
                val characters = if (response.isSuccessful && response.body() != null) {
                    response.body()?.results ?: emptyList()
                } else {
                    emptyList()
                }

                _uiState.value = _uiState.value.copy(
                    pictureList = characters
                )

                println("characters.size = ${characters.size}") // Отслеживаем получение списка
            } catch (e: Exception) {
                Log.d("PopularViewModel", e.message.toString())
                _uiState.value = _uiState.value.copy(isError = true, errorMessage = e.message ?: "Unknown error")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}
