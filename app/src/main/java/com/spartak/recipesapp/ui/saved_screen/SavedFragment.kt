package com.spartak.recipesapp.ui.saved_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.spartak.recipesapp.databinding.FragmentSavedBinding
import com.spartak.recipesapp.domain.model.Recype
import com.spartak.recipesapp.ui.home_screen.RecypeAdapter

class SavedFragment : Fragment() {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private var adapter: RecypeAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = RecypeAdapter(listOf(
            Recype(0, "Spartal", "Cace", "https://i.stack.imgur.com/GsDIl.jpg"),
            Recype(2, "Spartar", "Cace2", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGa804ujwQc2vYbvFAL--5238RpdCd4mPAcYNED7AmdA&s"),
            Recype(3, "Spartav", "Cace3","https://i.stack.imgur.com/GsDIl.jpg"),
        ))
        binding.recypeSavedRecycler.layoutManager = layoutManager
        binding.recypeSavedRecycler.adapter = adapter
    }

}