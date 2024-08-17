package com.example.galleryapp.new_fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.api.RetrofitClient
import com.example.galleryapp.data.ResultCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



data class NewFragmentUiState(
    val pictureList: List<ResultCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

class NewViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewFragmentUiState())

    val uiState
        get() = _uiState.asStateFlow()

    fun getPicture(){
        try{
            viewModelScope.launch (Dispatchers.IO) {
                _uiState.value = NewFragmentUiState(isLoading = true)

                val response = RetrofitClient.api.getAliveCharacters().execute()
                val characters = if(response.isSuccessful && response.body() != null){
                    response. body()?.results ?: emptyList()
                } else emptyList()
                _uiState.value = NewFragmentUiState(
                    pictureList = characters
                )
                println("characters.size = ${characters.size}") //Отслеживаем получение списка
            }
        } catch (e: Exception) {
            Log.d("NewViewModel", e.message.toString())
        }
        _uiState.value = NewFragmentUiState(isLoading = false)
    }
}