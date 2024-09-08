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


    var image: String?
        get() = state[imageKey]
        set(value) { state[imageKey] = value }

    var description: String?
        get() = state[descriptionKey]
        set(value) { state[descriptionKey] = value }


}