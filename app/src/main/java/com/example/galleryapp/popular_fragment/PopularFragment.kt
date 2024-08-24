package com.example.galleryapp.popular_fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.R
import com.example.galleryapp.databinding.FragmentNewBinding
import com.example.galleryapp.databinding.FragmentPopularBinding
import com.example.galleryapp.new_fragment.NewFragmentAdapter
import com.example.galleryapp.new_fragment.NewViewModel
import kotlinx.coroutines.launch


class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: PopularViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPicture()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =  FragmentPopularBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spanCount = if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            3
        }
        else{
            2
        }

        binding.rcViewPopular.layoutManager = GridLayoutManager(context, spanCount)


        val adapter = NewFragmentAdapter { selectedCharacter ->
            val bundle = Bundle().apply {
                putString("image", selectedCharacter.image)
                putString("description", selectedCharacter.name)
            }
            findNavController().navigate(R.id.action_popularFragment_to_detailFragment, bundle)

        }

        binding.rcViewPopular.adapter = adapter

        lifecycleScope.launch {
            viewModel.uiState.collect {
                println("collect state $it")
                adapter.updateData(it.pictureList)
                println("IsLoading ${it.isLoading}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}