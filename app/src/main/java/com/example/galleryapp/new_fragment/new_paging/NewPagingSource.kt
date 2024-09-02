package com.example.galleryapp.new_fragment.new_paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.galleryapp.api.ApiService
import com.example.galleryapp.data.ResultCharacter

class NewPagingSource(
    private val apiService: ApiService,
    private val name: String? = "morty",
) : PagingSource<Int, ResultCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultCharacter> {
        val page = params.key ?: 1

        return try {
            val response = apiService.getNewCharacters(name, page)


            if (response.isSuccessful) {
                val characters = response.body()?.results ?: emptyList()
                val nextPageNumber = if (characters.isNotEmpty()) page + 1 else null


                return LoadResult.Page(
                    data = characters,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = nextPageNumber
                )
            } else {
                LoadResult.Error(Exception("Ошибка загрузки данных (New): ${response.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
