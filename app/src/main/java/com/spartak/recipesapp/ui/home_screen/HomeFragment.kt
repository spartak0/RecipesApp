package com.spartak.recipesapp.ui.home_screen

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
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

    private val dialog by lazy {
        createCategoryDialog()
    }

    private val recipePagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecipePagingAdapter(
            recipeItemOnClick = { recipeId ->
                val action =
                    MainFragmentDirections.actionMainFragmentToDetailsFragment(recipeId)
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
                        createSnackbar(refreshState.error.localizedMessage ?: "")
                        viewModel.setIsLoading(true)
                    }

                    else -> viewModel.setIsLoading(refreshState == LoadState.Loading)
                }
            }
            etSearch.addTextChangedListener {
                viewModel.setSearchText(it.toString())
            }
        }

    }

    private fun setupUI() {
        with(binding) {
            rvRecipes.layoutManager = LinearLayoutManager(requireContext())
            rvRecipes.adapter = recipePagingAdapter
            tvViewAll.setOnClickListener {
                dialog.show()
            }
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
            viewModel.favoriteRecipes.observe(viewLifecycleOwner) { _ ->
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
                        viewModel.addRecipeInDb(
                            recipe = recipe.copy(isFavorite = true),
                            onError = { throwable ->
                                createSnackbar(
                                    throwable.localizedMessage ?: ""
                                )
                            })
                        view.setImageResource(R.drawable.save_filled)
                    }

                    true -> {
                        viewModel.deleteRecipeInDb(
                            recipe = recipe,
                            onError = { throwable ->
                                createSnackbar(
                                    throwable.localizedMessage ?: ""
                                )
                            })
                        view.setImageResource(R.drawable.save)
                    }
                }
            },
            onError = {},
        )
    }

    private fun createCategoryDialog(): AlertDialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.choose_sorting))
        val sortList = SortRecipes.getSortingList()
        val stringSortArray = sortList.map { it.value }.toTypedArray()
        builder.setItems(stringSortArray) { _, which ->
            val sorting = sortList[which]
            viewModel.setSortRecipes(sorting)
            when (sorting) {
                SortRecipes.NONE -> binding.btnGroupCategory.check(binding.btnNew.id)
                SortRecipes.Popularity -> binding.btnGroupCategory.check(binding.btnPopular.id)
                SortRecipes.Random -> binding.btnGroupCategory.check(binding.btnRandom.id)
                else -> {
                    binding.btnGroupCategory.clearCheck()
                }
            }
        }
        return builder.create()
    }

    private fun createSnackbar(text: String) {
        Snackbar.make(
            binding.root,
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }
}