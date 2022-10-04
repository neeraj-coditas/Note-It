package com.example.noteit.homescreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteit.databinding.FragmentHomeScreenBinding
import com.example.noteit.homescreen.adapter.HomeScreenRecyclerAdapter
import com.example.noteit.homescreen.viewmodel.HomeScreenViewModel

class HomeScreenFragment : Fragment() {
    private val viewModel: HomeScreenViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeScreenBinding
    private val notesAdapter = HomeScreenRecyclerAdapter()

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentHomeScreenRv.layoutManager = LinearLayoutManager(requireContext())
        binding.fragmentHomeScreenRv.adapter = notesAdapter

        viewModel.insertNote()
        viewModel.allNotes.observe(requireActivity()) {
            if (it.isNotEmpty()) {
                binding.fragmentHomeIv.visibility = View.GONE
                binding.fragmentHomeTvCreateNote.visibility = View.GONE
                notesAdapter.updateList(it)
            }
        }

        binding.fragmentHomeFabBtn.setOnClickListener{
            view.findNavController().navigate(HomeScreenFragmentDirections.actionHomeFragmentToEditorScreenFragment())
        }

    }
}