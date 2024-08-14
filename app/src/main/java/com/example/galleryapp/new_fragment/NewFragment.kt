package com.example.galleryapp.new_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryapp.R
import com.example.galleryapp.api.RetrofitClient
import com.example.galleryapp.data.ResultCharacter
import com.example.galleryapp.data.RickAndMortyInfo
import com.example.galleryapp.new_fragment.NewFragmentAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.getCharacters().execute()
                if (response.isSuccessful && response.body() != null) {
                    val characters = response.body()?.results ?: emptyList()
                    withContext(Dispatchers.Main) {

                        recyclerView = view.findViewById(R.id.rcView_new)
                        recyclerView.layoutManager = GridLayoutManager(context, 2)

                        adapter = NewFragmentAdapter(emptyList())
                        recyclerView.adapter = adapter //делаем пустой адаптер

                        adapter.updateData(characters) //обнова данных адаптера

                    }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Exception: ${e.message}")
            }
        }


    }





}

