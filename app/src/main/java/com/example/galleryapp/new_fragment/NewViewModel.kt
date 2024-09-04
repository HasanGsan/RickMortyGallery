package com.example.galleryapp.new_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.galleryapp.api.RetrofitClient
import com.example.galleryapp.data.ResultCharacter
import com.example.galleryapp.new_fragment.new_paging.NewPagingSource
import kotlinx.coroutines.flow.Flow


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
