package com.spartak.recipesapp.ui.home_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.repository.RecipeRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _recipes = MutableLiveData<List<Recipe>>(listOf())
    val recipes = _recipes

    init {
        fetchRecipe()
    }

    private fun fetchRecipe() {
        disposable.add(
            recipeRepository.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _recipes.value = it },
                    Throwable::printStackTrace
                )
        )
    }

}