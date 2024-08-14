package com.example.galleryapp.new_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.data.ResultCharacter

class NewFragmentAdapter(private var characters: List<ResultCharacter> = emptyList()) :
    RecyclerView.Adapter<NewFragmentAdapter.NewFragmentViewHolder>() {

//todo убери itemView.findViewById и замени на viewBinding

    inner class NewFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.imageView)

        fun bind(character: ResultCharacter){
            println("fun bind(character: ResultCharacter){ ${character.image}")
            Glide.with(itemView.context)
                .load(character.image)
                .placeholder(R.drawable.test_frame)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewFragmentAdapter.NewFragmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return NewFragmentViewHolder(view)
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