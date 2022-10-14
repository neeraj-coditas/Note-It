package com.example.noteit.ui.searchscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteit.databinding.FragmentSearchScreenBinding
import com.example.noteit.data.Note
import com.example.noteit.ui.searchscreen.adapter.SearchRecyclerAdapter
import com.example.noteit.viewmodel.NoteViewModel

class SearchScreenFragment : Fragment(), SearchView.OnQueryTextListener,
    SearchRecyclerAdapter.Interaction {

    private lateinit var binding: FragmentSearchScreenBinding
    private val viewModel: NoteViewModel by viewModels()
    private val searchAdapter = SearchRecyclerAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentSearchScreenRv.layoutManager = LinearLayoutManager(requireContext())
        binding.fragmentSearchScreenRv.adapter = searchAdapter

        binding.searchView.isSubmitButtonEnabled = false
        binding.searchView.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        viewModel.searchDatabase(query).observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.run {
                    fragmentSearchTvNoteNotFound.visibility = View.INVISIBLE
                    fragmentScreenIvNoteNotFound.visibility = View.INVISIBLE
                    fragmentSearchScreenRv.visibility = View.VISIBLE
                }
                searchAdapter.updateList(it)
            } else {
                binding.run {
                    fragmentScreenIvNoteNotFound.visibility = View.VISIBLE
                    fragmentSearchTvNoteNotFound.visibility = View.VISIBLE
                    fragmentSearchScreenRv.visibility = View.INVISIBLE
                }

            }
        }
    }

    override fun onItemSelected(item: Note) {
        val navDirection =
            SearchScreenFragmentDirections.actionSearchScreenFragmentToEditorScreenFragment(item)
        findNavController().navigate(navDirection)
    }
}