package com.spartak.recipesapp.ui.recycler_utils.paging

import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.RecipeCardBinding
import com.spartak.recipesapp.domain.model.Recipe

class RecipePagingAdapter(
    private val recipeItemOnClick: (Int) -> Unit,
    private val isFavoriteOnClick: (Recipe, View) -> Unit,
) : PagingDataAdapter<Recipe, RecipePagingAdapter.RecipeHolder>(RecipeDiffUtilItemCallback) {

    inner class RecipeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecipeCardBinding.bind(view)

        fun bind(recipe: Recipe) {
            with(binding) {
                tvName.text = recipe.name
                Glide.with(ivRecipe).load(recipe.image).into(ivRecipe)
                btnFavorite.setImageIcon(
                    Icon.createWithResource(
                        binding.root.context.applicationContext,
                        if (recipe.isFavorite) R.drawable.save_filled else R.drawable.save
                    )
                )
            }
            setupListeners(recipe)
        }

        private fun setupListeners(recipe: Recipe) {
            with(binding) {
                ivRecipe.setOnClickListener {
                    recipeItemOnClick(recipe.id)
                }
                btnFavorite.setOnClickListener {
                    isFavoriteOnClick(recipe, it)
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
