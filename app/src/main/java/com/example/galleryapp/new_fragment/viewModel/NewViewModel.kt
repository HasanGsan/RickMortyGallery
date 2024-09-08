package com.example.galleryapp.new_fragment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.galleryapp.api.RetrofitClient
import com.example.galleryapp.data.ResultCharacter
import com.example.galleryapp.new_fragment.newPagingSource.NewPagingSource
import kotlinx.coroutines.flow.Flow


class NewViewModel : ViewModel() {
    val characters: Flow<PagingData<ResultCharacter>> = Pager(
        config = PagingConfig( // Вид отображения пагинации
            pageSize = 6,
            initialLoadSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { NewPagingSource(RetrofitClient.api) }
    ).flow
        .cachedIn(viewModelScope)
}
