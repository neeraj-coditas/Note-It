package com.example.noteit.searchscreen

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteit.R
import com.example.noteit.databinding.FragmentSearchScreenBinding
import com.example.noteit.homescreen.HomeScreenFragmentDirections
import com.example.noteit.homescreen.adapter.HomeScreenRecyclerAdapter
import com.example.noteit.homescreen.viewmodel.HomeScreenViewModel
import com.example.noteit.model.Note
import com.example.noteit.searchscreen.adapter.SearchScreenRecyclerAdapter

class SearchScreenFragment : Fragment(), SearchView.OnQueryTextListener, SearchScreenRecyclerAdapter.Interaction{

    private lateinit var binding: FragmentSearchScreenBinding
    private lateinit var viewModel: HomeScreenViewModel
    private val searchAdapter = SearchScreenRecyclerAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchScreenBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
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
        val searchQuery = "%$query%"
       viewModel.searchDatabase(searchQuery)
        viewModel.readData?.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                binding.fragmentSearchTvNoteNotFound.visibility = View.INVISIBLE
                binding.fragmentScreenIvNoteNotFound.visibility = View.INVISIBLE
                binding.fragmentSearchScreenRv.visibility = View.VISIBLE
                searchAdapter.updateList(it)
            }
            else{
                binding.fragmentScreenIvNoteNotFound.visibility = View.VISIBLE
                binding.fragmentSearchTvNoteNotFound.visibility = View.VISIBLE
                binding.fragmentSearchScreenRv.visibility = View.INVISIBLE
            }
        }
    }

    override fun onItemSelected(item: Note) {
        val navDirection = SearchScreenFragmentDirections.actionSearchScreenFragmentToEditorScreenFragment(item)
        findNavController().navigate(navDirection)
    }
}