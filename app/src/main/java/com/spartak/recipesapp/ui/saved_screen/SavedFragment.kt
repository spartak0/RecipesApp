package com.spartak.recipesapp.ui.saved_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.FragmentSavedBinding
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.ui.home_screen.RecipeAdapter
import com.spartak.recipesapp.ui.main_screen.MainFragmentDirections

class SavedFragment : Fragment(R.layout.fragment_saved) {
    private val binding: FragmentSavedBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.rvRecipes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = RecipeAdapter(
                listOf(
                    Recipe(
                        0,
                        "Spartal",
                        "https://i.stack.imgur.com/GsDIl.jpg",
                        false,
                    ),
                    Recipe(
                        3,
                        "Spartav",
                        "https://i.stack.imgur.com/GsDIl.jpg",
                        false,
                    ),
                )
            ) { recipe ->
                val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(recipe)
                findNavController().navigate(action)
            }
        }
    }

}