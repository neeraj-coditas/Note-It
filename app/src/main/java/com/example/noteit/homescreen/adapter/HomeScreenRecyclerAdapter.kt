package com.example.noteit.homescreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteit.data.Note
import com.example.noteit.databinding.SingleNoteViewBinding

class HomeScreenRecyclerAdapter(private val notes: List<Note>):RecyclerView.Adapter<HomeScreenRecyclerAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = SingleNoteViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val myList = notes[position]
        holder.bind(myList)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NotesViewHolder(val binding: SingleNoteViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(view: Note){
            binding.notesDataClass = view
        }
    }

}