package com.example.galleryapp.detailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.galleryapp.R
import com.example.galleryapp.databinding.PopupWindowBinding

class DetailViewModel(private val state: SavedStateHandle) : ViewModel() {

    private var imageKey = "image"
    private var descriptionKey = "description"

    private val _isToolbarVisible = MutableLiveData(true)
    val isToolbarVisible: LiveData<Boolean> = _isToolbarVisible

    private val _isBackButton = MutableLiveData(false)
    val isBackButton: LiveData<Boolean> = _isBackButton

    var image: String?
        get() = state[imageKey]
        set(value) { state[imageKey] = value }

    var description: String?
        get() = state[descriptionKey]
        set(value) { state[descriptionKey] = value }




    // Управление отображением заголовка
    fun offToolBar() {
        _isToolbarVisible.value = false
    }

    fun onToolBar() {
        _isToolbarVisible.value = true
    }

    // Управление отображением Назад
    fun offBackButton() {
        _isBackButton.value = false
    }

    fun onBackButton() {
        _isBackButton.value = true
    }



}