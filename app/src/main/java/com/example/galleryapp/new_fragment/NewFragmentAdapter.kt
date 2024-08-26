package com.example.galleryapp.new_fragment

import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.data.ResultCharacter
import com.example.galleryapp.databinding.ItemCharacterBinding

class NewFragmentAdapter(
    private var characters: List<ResultCharacter> = emptyList(),
    private val onItemClicked: (ResultCharacter) -> Unit
    ) : RecyclerView.Adapter<NewFragmentAdapter.NewFragmentViewHolder>() {

    inner class NewFragmentViewHolder(private val bindingImg: ItemCharacterBinding) : RecyclerView.ViewHolder(bindingImg.root) {

        fun bind(character: ResultCharacter){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewFragmentAdapter.NewFragmentViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewFragmentAdapter.NewFragmentViewHolder, position: Int) {
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