package com.spartak.recipesapp.ui.recycler_utils.standart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.RecipeCardBinding
import com.spartak.recipesapp.domain.model.Recipe


class RecipeAdapter(
    private val list: MutableList<Recipe> = mutableListOf(),
    private val recipeItemOnClick: (Int) -> Unit,
    private val isFavoriteOnClick: (Recipe, View) -> Unit,
) : RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {

    inner class RecipeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecipeCardBinding.bind(view)

        fun bind(recipe: Recipe) {
            with(binding) {
                tvName.text = recipe.name
                Glide.with(ivRecipe).load(recipe.image).into(ivRecipe)
                btnFavorite.setImageResource(
                    if (recipe.isFavorite) R.drawable.save_filled else R.drawable.save
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


    fun update(newList: List<Recipe>) {
        val diffCallback = RecipeDiffUtilCallback(list, newList)
        val diffRecipes = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffRecipes.dispatchUpdatesTo(this)
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