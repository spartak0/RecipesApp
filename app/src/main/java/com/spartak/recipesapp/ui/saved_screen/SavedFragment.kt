package com.spartak.recipesapp.ui.saved_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spartak.recipesapp.R
import com.spartak.recipesapp.databinding.FragmentSavedBinding
import com.spartak.recipesapp.domain.model.Recipe
import com.spartak.recipesapp.ui.home_screen.RecipeAdapter

class SavedFragment : Fragment(R.layout.fragment_saved) {
    private val binding: FragmentSavedBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerRecipes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = RecipeAdapter(
                listOf(
                    Recipe(0, "Spartal", "Cace", "https://i.stack.imgur.com/GsDIl.jpg"),
                    Recipe(2, "Spartar", "Cace2", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGa804ujwQc2vYbvFAL--5238RpdCd4mPAcYNED7AmdA&s"),
                    Recipe(3, "Spartav", "Cace3", "https://i.stack.imgur.com/GsDIl.jpg"),
                )
            )
        }
    }

}