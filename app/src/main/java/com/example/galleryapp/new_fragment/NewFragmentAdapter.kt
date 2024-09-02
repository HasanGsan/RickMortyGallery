package com.example.galleryapp.new_fragment

import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingConfig
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.data.ResultCharacter
import com.example.galleryapp.databinding.ItemCharacterBinding

class NewFragmentAdapter(
    private val onItemClicked: (ResultCharacter) -> Unit,
) : PagingDataAdapter<ResultCharacter, NewFragmentAdapter.NewFragmentViewHolder>(DiffCallback) {

    inner class NewFragmentViewHolder(private val bindingImg: ItemCharacterBinding) :
        RecyclerView.ViewHolder(bindingImg.root) {

        fun bind(character: ResultCharacter) {
            println("fun bind(character: ResultCharacter){ ${character.image}")

            Glide.with(bindingImg.root)
                .load(character.image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(bindingImg.imageView)


            bindingImg.root.setOnClickListener { //Слушатель кликов
                onItemClicked(character)
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewFragmentAdapter.NewFragmentViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewFragmentAdapter.NewFragmentViewHolder, position: Int) {
        val character = getItem(position)
        if (character != null) {
            holder.bind(character)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ResultCharacter>() {
        override fun areItemsTheSame(oldItem: ResultCharacter, newItem: ResultCharacter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResultCharacter,
            newItem: ResultCharacter
        ): Boolean {
            return oldItem == newItem
        }


    }

}