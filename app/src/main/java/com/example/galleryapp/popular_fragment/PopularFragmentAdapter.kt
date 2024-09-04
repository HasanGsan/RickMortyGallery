package com.example.galleryapp.popular_fragment

import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.galleryapp.R
import com.example.galleryapp.data.ResultCharacter
import com.example.galleryapp.databinding.ItemCharacterBinding
import com.example.galleryapp.new_fragment.NewFragmentAdapter


class PopularFragmentAdapter(
    private val onItemClicked: (ResultCharacter) -> Unit,
) : PagingDataAdapter<ResultCharacter, PopularFragmentAdapter.PopularFragmentViewHolder>(
    DiffCallback
) {

    inner class PopularFragmentViewHolder(private val bindingImg: ItemCharacterBinding) :
        RecyclerView.ViewHolder(bindingImg.root) {

        fun bind(character: ResultCharacter) {
            println("fun bind(character: ResultCharacter){ ${character.image}")

            Glide.with(bindingImg.root)
                .load(character.image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_launcher_foreground)
                .into(bindingImg.imageView)


            bindingImg.root.setOnClickListener { //Слушатель кликов к постам
                onItemClicked(character)
            }

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularFragmentAdapter.PopularFragmentViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PopularFragmentAdapter.PopularFragmentViewHolder,
        position: Int
    ) {
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