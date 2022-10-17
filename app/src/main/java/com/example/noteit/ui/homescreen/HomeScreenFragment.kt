package com.example.noteit.ui.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteit.data.Note
import com.example.noteit.databinding.FragmentHomeScreenBinding
import com.example.noteit.ui.homescreen.adapter.NotesRecyclerAdapter
import com.example.noteit.viewmodel.NoteViewModel

class HomeScreenFragment : Fragment(), NotesRecyclerAdapter.Interaction {
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var binding: FragmentHomeScreenBinding
    private var isItemSelected = false
    private val notesAdapter by lazy {
        NotesRecyclerAdapter(this)
    }
    private val onBackPressImpl = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isItemSelected) {
                notesAdapter.deselectItem()
                isItemSelected = false
            } else {
                activity?.finish()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRV()
        subscribeBackpress()
        subscribeFabClickListener()
        subscribeSearchClickListener()

    }

    private fun subscribeSearchClickListener() {
        binding.fragmentHomeIvSearchNote.setOnClickListener {
            view?.findNavController()
                ?.navigate(HomeScreenFragmentDirections.actionHomeFragmentToSearchScreenFragment())
        }
    }

    private fun subscribeFabClickListener() {
        binding.fragmentHomeFabBtn.setOnClickListener {
            val emptyList = Note("", "", 0)
            view?.findNavController()?.navigate(
                HomeScreenFragmentDirections.actionHomeFragmentToEditorScreenFragment(emptyList)
            )
        }
    }

    private fun subscribeBackpress() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, onBackPressImpl)
    }


    private fun initRV() {
        binding.fragmentHomeScreenRv.layoutManager = LinearLayoutManager(requireContext())
        binding.fragmentHomeScreenRv.adapter = notesAdapter


        viewModel.allNotes.observe(requireActivity()) {
            if (it.isNotEmpty()) {
                binding.fragmentHomeIv.visibility = View.GONE
                binding.fragmentHomeTextCreateNote.visibility = View.GONE
                notesAdapter.updateList(it)
            } else {
                notesAdapter.updateList(it)
                binding.fragmentHomeIv.visibility = View.VISIBLE
                binding.fragmentHomeTextCreateNote.visibility = View.VISIBLE
            }
        }
    }

    override fun onItemSelected(item: Note) {
        val navDirection =
            HomeScreenFragmentDirections.actionHomeFragmentToEditorScreenFragment(item)
        findNavController().navigate(navDirection)
    }

    override fun onClickDelete(item: Note) {
        viewModel.deleteNote(item)
    }

    override fun onItemDeselect() {
        isItemSelected = true
    }

}