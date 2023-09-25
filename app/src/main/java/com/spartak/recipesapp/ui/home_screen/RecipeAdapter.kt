package com.spartak.recipesapp.ui.home_screen

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.RecipeCardBinding
import com.spartak.recipesapp.domain.model.Recipe

class RecipeAdapter(
    private val list: List<Recipe>,
    private val recipeItemOnClick: (Recipe) -> Unit
) :
    RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {
    inner class RecipeHolder(view: View) : RecyclerView.ViewHolder(view), OnClickListener {
        private val binding = RecipeCardBinding.bind(view)

        init {
            view.setOnClickListener(this)
        }

        fun bind(recipe: Recipe) {
            with(binding) {
                tvAuthor.text = recipe.author
                tvName.text = recipe.name
                Glide.with(ivRecipe).load(recipe.image).into(ivRecipe)
            }

        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                recipeItemOnClick(list[position])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_card, parent, false)
        return RecipeHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val recipe = list[position]
        holder.bind(recipe)
    }
}
