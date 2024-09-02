package com.example.galleryapp.new_fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.galleryapp.api.ApiService
import com.example.galleryapp.api.RetrofitClient
import com.example.galleryapp.data.ResultCharacter
import com.example.galleryapp.new_fragment.new_paging.NewPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class NewViewModel : ViewModel() {

    val characters: Flow<PagingData<ResultCharacter>> = Pager(
        config = PagingConfig(
            pageSize = 6,
            initialLoadSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { NewPagingSource(RetrofitClient.api) }
    ).flow
        .cachedIn(viewModelScope)
}