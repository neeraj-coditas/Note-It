package com.example.noteit.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteit.databinding.FragmentHomeScreenBinding
import com.example.noteit.homescreen.adapter.HomeScreenRecyclerAdapter
import com.example.noteit.homescreen.viewmodel.HomeScreenViewModel

class HomeScreenFragment : Fragment() {
    private lateinit var vm : HomeScreenViewModel
    private lateinit var binding : FragmentHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(requireActivity())[HomeScreenViewModel::class.java]
    }

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentHomeScreenBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isEmpty = vm.notesObjects.isEmpty()
        if(!isEmpty){
            binding.fragmentHomeIv.visibility = View.GONE
            binding.fragmentHomeTvCreateNote.visibility = View.GONE

            binding.notesList.layoutManager = LinearLayoutManager(requireContext())
            val myList = vm.notesObjects
            binding.notesList.adapter = HomeScreenRecyclerAdapter(myList)
        }
    }
}