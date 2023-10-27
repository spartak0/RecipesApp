package com.spartak.recipesapp.domain.model

sealed class PagerState {
    class Search(val title: String, val sortRecipes: SortRecipes) : PagerState()
    class Default(val sortRecipes: SortRecipes) : PagerState()
}