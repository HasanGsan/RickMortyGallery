package com.example.galleryapp.popular_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.galleryapp.api.RetrofitClient
import com.example.galleryapp.data.ResultCharacter
import com.example.galleryapp.popular_fragment.popular_paging.PopularPagingSource
import kotlinx.coroutines.flow.Flow


class PopularViewModel : ViewModel() {

    val characters: Flow<PagingData<ResultCharacter>> = Pager(
        config = PagingConfig(
            pageSize = 6,
            initialLoadSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PopularPagingSource(RetrofitClient.api) }
    ).flow
        .cachedIn(viewModelScope)

}
