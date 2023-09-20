package com.spartak.recipesapp.ui.home_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.RecypeCardBinding
import com.spartak.recipesapp.domain.model.Recype

class RecypeAdapter(private val list: List<Recype>) :
    RecyclerView.Adapter<RecypeAdapter.RecypeHolder>() {
    class RecypeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecypeCardBinding.bind(view)
        fun bind(recype: Recype) {
            with(binding) {
                authorTV.text = recype.author
                recipeNameTV.text = recype.name
                Glide.with(recipeImageView).load(recype.image).into(recipeImageView)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecypeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recype_card, parent, false)
        return RecypeHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecypeHolder, position: Int) {
        holder.bind(list[position])
    }
}