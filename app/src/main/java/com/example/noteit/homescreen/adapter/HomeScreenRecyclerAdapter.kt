package com.example.noteit.homescreen.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.noteit.databinding.SingleNoteViewBinding
import com.example.noteit.model.Note

class HomeScreenRecyclerAdapter() : RecyclerView.Adapter<HomeScreenRecyclerAdapter.NotesViewHolder>() {

    val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = SingleNoteViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.bind(currentNote)
    }

    fun updateList(newList : List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        Log.d("ListCheckAtAdapter",newList.toString())
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    class NotesViewHolder(val binding: SingleNoteViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(view: Note){
            binding.notesDataClass = view
        }
    }

}