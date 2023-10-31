package com.spartak.recipesapp.domain.model

enum class SortRecipes(val value: String) {
    NONE(""),
    Popularity("popularity"),
    Random("random"),
    Caffeine("caffeine"),
    Energy("energy"),
    Healthiness("healthiness"),
    Price("price"),
    Calories("calories"),
    Alcohol("alcohol");

    companion object {
        fun getSortingList(): List<SortRecipes> = SortRecipes.values().toMutableList().apply {
            remove(NONE)
        }
    }
}