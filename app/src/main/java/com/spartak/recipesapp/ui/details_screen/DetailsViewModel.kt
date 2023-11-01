package com.spartak.recipesapp.ui.details_screen

import androidx.lifecycle.MutableLiveData
import com.spartak.recipesapp.domain.model.RecipeInfo
import com.spartak.recipesapp.domain.repository.RecipeRepository
import com.spartak.recipesapp.ui.DisposableViewModel
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : DisposableViewModel() {

    private val _recipeInfo = MutableLiveData<RecipeInfo>()
    val recipeInfo = _recipeInfo

    fun fetchRecipeInfo(id: Int, onComplete: () -> Unit) {
        recipeRepository.getRecipeInfo(id)
            .applySchedulers(
                {
                    _recipeInfo.value = it
                    onComplete()
                },
                Throwable::printStackTrace,
            )
    }
}