package com.example.noteit.searchscreen.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteit.R
import com.example.noteit.databinding.SingleNoteViewBinding
import com.example.noteit.model.Note

class SearchScreenRecyclerAdapter(private val interaction: Interaction) : RecyclerView.Adapter<SearchScreenRecyclerAdapter.NotesViewHolder>() {

    val allNotes = ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = SingleNoteViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesViewHolder(binding,interaction)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(allNotes[position])
        assignColor(holder)
    }

    private fun assignColor(holder: NotesViewHolder) {
        val background = holder.itemView.background
        background.setTint(Color.parseColor("#FF000000"))
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

    class NotesViewHolder(val binding: SingleNoteViewBinding, private val interaction: SearchScreenRecyclerAdapter.Interaction)  : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note){
            binding.viewUnitTvTitle.setTextColor(Color.parseColor("#FFFFFF"))
            binding.notesDataClass = item

            itemView.setOnClickListener {
                interaction.onItemSelected(item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(item: Note)
    }


}