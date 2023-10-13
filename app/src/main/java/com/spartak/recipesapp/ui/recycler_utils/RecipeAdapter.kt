package com.spartak.recipesapp.ui.recycler_utils

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.RecipeCardBinding
import com.spartak.recipesapp.domain.model.Recipe

class RecipeAdapter(
    private val recipeItemOnClick: (Int) -> Unit
) : PagingDataAdapter<Recipe, RecipeAdapter.RecipeHolder>(RecipeDiffUtilItemCallback) {

    inner class RecipeHolder(view: View) : RecyclerView.ViewHolder(view), OnClickListener {
        private val binding = RecipeCardBinding.bind(view)

        init {
            view.setOnClickListener(this)
        }

        fun bind(recipe: Recipe) {
            with(binding) {
                tvName.text = recipe.name
                Glide.with(ivRecipe).load(recipe.image).into(ivRecipe)
            }
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                getItem(position)?.let {
                    recipeItemOnClick(it.id)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_card, parent, false)
        return RecipeHolder(view)
    }

}
