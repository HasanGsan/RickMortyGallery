package com.example.galleryapp.popular_fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryapp.R
import com.example.galleryapp.databinding.FragmentPopularBinding
import com.example.galleryapp.new_fragment.NewFragmentAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: PopularViewModel by viewModels()
    private lateinit var adapter: PopularFragmentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spanCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                3
            } else {
                2
            }

        binding.rcViewPopular.layoutManager = GridLayoutManager(context, spanCount)


        adapter = PopularFragmentAdapter { selectedCharacter ->
            // Если пост был просмотрен, обрабатываем
            val bundle = Bundle().apply {
                putString("image", selectedCharacter.image)
                putString("description", selectedCharacter.name)
            }
            findNavController().navigate(R.id.action_popularFragment_to_detailFragment, bundle)

        }

        binding.rcViewPopular.adapter = adapter

        // Добавляем слушатель для подгрузки данных при прокрутке вниз

        binding.rcViewPopular.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rcViewPopular.canScrollVertically(1) && dy > 0) {
                    binding.popularProgressBar.visibility = View.VISIBLE
                    adapter.retry()
                }
            }
        })

        adapter.addLoadStateListener { loadState -> //Состояния Popular Progress Bar
            when (loadState.source.refresh) {
                is LoadState.Loading -> binding.popularProgressBar.visibility = View.VISIBLE
                is LoadState.NotLoading -> binding.popularProgressBar.visibility = View.GONE
                is LoadState.Error -> binding.popularProgressBar.visibility = View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characters.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}