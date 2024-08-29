package com.example.galleryapp.detailFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.databinding.FragmentNewBinding
import com.example.galleryapp.databinding.PopupWindowBinding

class DetailFragment : Fragment() {

    private var _binding: PopupWindowBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private  val supportActionBar = (activity as? AppCompatActivity)?.supportActionBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopupWindowBinding.inflate(inflater, container, false)
        return binding.root
    }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            binding.backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }

            val image = viewModel.image
            val description = viewModel.description

            Glide.with(this)
                .load(image)
                .into(binding.windowToImage)

            binding.titleWindowText.text = description
    }

    //TODO реализовать кастомную back_arrow правильно 

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onPause() {
        super.onPause()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}