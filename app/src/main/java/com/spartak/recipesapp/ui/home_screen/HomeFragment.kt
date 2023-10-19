package com.spartak.recipesapp.ui.home_screen

import android.content.Context
import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.spartak.recipesapp.R
import com.spartak.recipesapp.app.appComponent
import com.spartak.recipesapp.databinding.FragmentHomeBinding
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.domain.model.SortRecipes
import com.spartak.recipesapp.ui.main_screen.MainFragmentDirections
import com.spartak.recipesapp.ui.recycler_utils.paging.RecipePagingAdapter
import javax.inject.Inject


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels() {
        viewModelFactory
    }

    private val recipePagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecipePagingAdapter(
            recipeItemOnClick = { recipeId ->
                val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(recipeId)
                findNavController().navigate(action)
            },
            isFavoriteOnClick = { recipe, view ->
                isFavoriteOnClick(recipe, view as ImageView)
            },
        )
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupListeners()
        setupObservables()
    }

    private fun setupListeners() {
        with(binding) {
            btnGroupCategory.setOnCheckedChangeListener { _, checkedId ->
                viewModel.setSortRecipes(getSortRecipesByBtnId(checkedId))
            }
            recipePagingAdapter.addLoadStateListener { loadStates: CombinedLoadStates ->
                val refreshState = loadStates.refresh
                when (refreshState) {
                    is LoadState.Error -> {
                        Snackbar.make(
                            binding.root,
                            refreshState.error.localizedMessage ?: "",
                            Snackbar.LENGTH_LONG
                        ).show()
                        viewModel.setIsLoading(true)
                    }

                    else -> viewModel.setIsLoading(refreshState == LoadState.Loading)
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.recipes.value?.let { recipePagingAdapter.submitData(lifecycle, it) }
    }

    private fun setupUI() {
        with(binding.rvRecipes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipePagingAdapter
        }
    }

    private fun setupObservables() {
        with(binding) {
            viewModel.recipes.observe(viewLifecycleOwner) {
                (rvRecipes.adapter as RecipePagingAdapter).submitData(lifecycle, it)

            }
            viewModel.isLoading.observe(viewLifecycleOwner) {
                rvRecipes.isVisible = !it
                pbRecipes.isVisible = it
            }
            viewModel.favoriteRecipes.observe(viewLifecycleOwner) { list ->
                (rvRecipes.adapter as RecipePagingAdapter).refresh()
            }

        }

    }

    private fun getSortRecipesByBtnId(checkedId: Int) =
        with(binding) {
            when (checkedId) {
                btnNew.id -> SortRecipes.NONE
                btnPopular.id -> SortRecipes.Popularity
                btnRandom.id -> SortRecipes.Random
                else -> SortRecipes.NONE
            }
        }

    private fun isFavoriteOnClick(recipe: Recipe, view: ImageView) {
        viewModel.existRecipe(
            recipe.id,
            onSuccess = {
                when (it) {
                    false -> {
                        viewModel.addRecipeInDb(recipe.copy(isFavorite = true))
                        view.setImageIcon(
                            Icon.createWithResource(
                                context?.applicationContext,
                                R.drawable.save_filled
                            )
                        )
                    }

                    true -> {
                        viewModel.deleteRecipeInDb(recipe)
                        view.setImageIcon(
                            Icon.createWithResource(
                                context?.applicationContext,
                                R.drawable.save
                            )
                        )
                    }
                }
            },
            onError = {},
        )
    }
}