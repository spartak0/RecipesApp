package com.spartak.recipesapp.ui.details_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spartak.recipesapp.domain.model.RecipeInfo
import com.spartak.recipesapp.domain.repository.RecipeRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _recipeInfo = MutableLiveData<RecipeInfo>()
    val recipeInfo = _recipeInfo

    fun fetchRecipeInfo(id: Int) {
        disposable.add(
            recipeRepository.getRecipeInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _recipeInfo.value = it},
                    Throwable::printStackTrace
                )
        )


    }
}