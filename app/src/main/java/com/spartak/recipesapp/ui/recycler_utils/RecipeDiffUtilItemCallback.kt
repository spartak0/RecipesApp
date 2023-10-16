package com.spartak.recipesapp.ui.recycler_utils

import androidx.recyclerview.widget.DiffUtil
import com.spartak.recipesapp.domain.model.Recipe

object RecipeDiffUtilItemCallback : DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.name == newItem.name && oldItem.image == newItem.image
    }
}