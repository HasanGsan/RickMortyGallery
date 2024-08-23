package com.example.galleryapp.detailFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.galleryapp.databinding.FragmentNewBinding
import com.example.galleryapp.databinding.PopupWindowBinding

class DetailFragment : Fragment() {

    private var _binding: PopupWindowBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopupWindowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() { //Сокрытие заголовка и отображение кнопки назад
        super.onResume()

        val supportActionBar = (activity as? AppCompatActivity)?.supportActionBar //общая переменная для настройки

        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onPause() {
        super.onPause()

        val supportActionBar = (activity as? AppCompatActivity)?.supportActionBar //общая переменная для настройки

        supportActionBar?.apply { // Сокрытие кнопки назад и отображение заголвока
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(false)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val image = viewModel.image
            val description = viewModel.description

            Glide.with(this)
                .load(image)
                .into(binding.windowToImage)

            binding.titleWindowText.text = description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}