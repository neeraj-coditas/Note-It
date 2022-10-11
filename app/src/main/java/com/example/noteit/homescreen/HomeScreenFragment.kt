package com.example.noteit.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteit.databinding.FragmentHomeScreenBinding
import com.example.noteit.homescreen.adapter.HomeScreenRecyclerAdapter
import com.example.noteit.homescreen.viewmodel.HomeScreenViewModel
import com.example.noteit.model.Note

class HomeScreenFragment : Fragment(), HomeScreenRecyclerAdapter.Interaction {
    private lateinit var  viewModel: HomeScreenViewModel
    private lateinit var binding: FragmentHomeScreenBinding
    private val notesAdapter = HomeScreenRecyclerAdapter(this)

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentHomeScreenRv.layoutManager = LinearLayoutManager(requireContext())
        binding.fragmentHomeScreenRv.adapter = notesAdapter

        viewModel.allNotes.observe(requireActivity()) {
            if (it.isNotEmpty()) {
                binding.fragmentHomeIv.visibility = View.GONE
                binding.fragmentHomeTextCreateNote.visibility = View.GONE
                notesAdapter.updateList(it)
            }
            else{
                notesAdapter.updateList(it)
                binding.fragmentHomeIv.visibility = View.VISIBLE
                binding.fragmentHomeTextCreateNote.visibility = View.VISIBLE
            }
        }

        binding.fragmentHomeFabBtn.setOnClickListener{
            val emptyList = Note("","")
            view.findNavController().navigate(HomeScreenFragmentDirections.actionHomeFragmentToEditorScreenFragment(emptyList))
        }

        binding.fragmentHomeIvSearchNote.setOnClickListener{
            view.findNavController().navigate(HomeScreenFragmentDirections.actionHomeFragmentToSearchScreenFragment())
        }

    }

    override fun onItemSelected(item: Note) {
        val navDirection = HomeScreenFragmentDirections.actionHomeFragmentToEditorScreenFragment(item)
        findNavController().navigate(navDirection)
    }

    override fun onClickDelete(item: Note) {
        viewModel.deleteNote(item)
    }

}