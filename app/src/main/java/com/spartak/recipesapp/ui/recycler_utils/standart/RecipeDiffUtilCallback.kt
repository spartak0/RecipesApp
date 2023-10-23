package com.spartak.recipesapp.ui.recycler_utils.standart

import androidx.recyclerview.widget.DiffUtil
import com.spartak.recipesapp.domain.model.Recipe

class RecipeDiffUtilCallback(
    private val oldList: List<Recipe>, private val newList: List<Recipe>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldValue = oldList[oldItemPosition]
        val newValue = newList[newItemPosition]
        return oldValue.id == newValue.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldValue = oldList[oldItemPosition]
        val newValue = newList[newItemPosition]
        return oldValue == newValue
    }

}