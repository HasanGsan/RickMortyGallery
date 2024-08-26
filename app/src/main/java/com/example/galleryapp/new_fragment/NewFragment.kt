package com.example.galleryapp.new_fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.R
import com.example.galleryapp.databinding.FragmentNewBinding
import kotlinx.coroutines.launch

class NewFragment : Fragment() {

    private var _binding: FragmentNewBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: NewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPicture()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewBinding.inflate(inflater, container, false)
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

        binding.rcViewNew.layoutManager = GridLayoutManager(context, spanCount)


        val adapter = NewFragmentAdapter { selectedCharacter ->
            val bundle = Bundle().apply {
                putString("image", selectedCharacter.image)
                putString("description", selectedCharacter.name)
            }
            findNavController().navigate(R.id.action_newFragment_to_detailFragment, bundle)
        }

        binding.rcViewNew.adapter = adapter

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
