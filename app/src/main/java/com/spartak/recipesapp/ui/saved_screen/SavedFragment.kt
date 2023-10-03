package com.spartak.recipesapp.ui.saved_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.FragmentSavedBinding
import com.spartak.recipesapp.ui.main_screen.MainFragmentDirections
import com.spartak.recipesapp.ui.recycler_utils.RecipeAdapter

class SavedFragment : Fragment(R.layout.fragment_saved) {
    private val binding: FragmentSavedBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        with(binding.rvRecipes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = RecipeAdapter() { recipeId ->
                val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(recipeId)
                findNavController().navigate(action)
            }
        }
    }
}