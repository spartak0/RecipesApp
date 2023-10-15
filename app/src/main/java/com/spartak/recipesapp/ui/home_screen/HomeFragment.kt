package com.spartak.recipesapp.ui.home_screen

import android.content.Context
import android.os.Bundle
import android.view.View
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
import com.spartak.recipesapp.domain.model.SortRecipes
import com.spartak.recipesapp.ui.main_screen.MainFragmentDirections
import com.spartak.recipesapp.ui.recycler_utils.RecipeAdapter
import javax.inject.Inject


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels() {
        viewModelFactory
    }
    private val recipeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecipeAdapter { recipeId ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(recipeId)
            findNavController().navigate(action)
        }
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
            recipeAdapter.addLoadStateListener { loadStates: CombinedLoadStates ->
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

    private fun setupUI() {
        with(binding.rvRecipes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }
    }

    private fun setupObservables() {
        with(binding) {
            viewModel.recipes.observe(viewLifecycleOwner) {
                (rvRecipes.adapter as RecipeAdapter).submitData(lifecycle, it)
            }
            viewModel.isLoading.observe(viewLifecycleOwner) {
                rvRecipes.isVisible = !it
                pbRecipes.isVisible = it
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

}