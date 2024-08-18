package com.example.galleryapp.popular_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.data.ResultCharacter
import com.example.galleryapp.databinding.ItemCharacterBinding


class PopularFragmentAdapter(private var characters: List<ResultCharacter> = emptyList()) :
 RecyclerView.Adapter<PopularFragmentAdapter.PopularFragmentViewHolder>()  {

    inner class PopularFragmentViewHolder(private val bindingImg: ItemCharacterBinding) : RecyclerView.ViewHolder(bindingImg.root) {

        fun bind(character: ResultCharacter){
            println("fun bind(character: ResultCharacter){ ${character.image}")
            Glide.with(bindingImg.root)
                .load(character.image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(bindingImg.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFragmentAdapter.PopularFragmentViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularFragmentAdapter.PopularFragmentViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    fun updateData(newCharacters: List<ResultCharacter>){
        characters = newCharacters
        notifyDataSetChanged() //уведа об изменении
    }
}