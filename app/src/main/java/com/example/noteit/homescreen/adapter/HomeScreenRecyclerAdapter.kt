package com.example.noteit.homescreen.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteit.R
import com.example.noteit.databinding.SingleNoteViewBinding
import com.example.noteit.model.Note

class HomeScreenRecyclerAdapter(private val interaction: Interaction) : RecyclerView.Adapter<HomeScreenRecyclerAdapter.NotesViewHolder>() {

    val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = SingleNoteViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesViewHolder(binding,interaction)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(allNotes[position])
        holder.itemView.isLongClickable
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

    class NotesViewHolder(val binding: SingleNoteViewBinding, private val interaction: Interaction) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Note){
            binding.notesDataClass = item

            itemView.setOnClickListener {
                interaction.onItemSelected(item)
            }

            itemView.setOnLongClickListener {
                binding.apply {
                    viewUnitTvTitle.visibility = View.INVISIBLE
                    singleNoteIvDelete.visibility = View.VISIBLE
                    singleNote.setBackgroundResource(R.drawable.delete_note_shape)
                    singleNote.setOnClickListener{
                        interaction.onClickDelete(item)
                        singleNote.setBackgroundResource(R.drawable.single_note_shape)
                        singleNoteIvDelete.visibility = View.GONE
                        viewUnitTvTitle.visibility = View.VISIBLE
                    }
                }
                true
            }
        }
    }

    interface Interaction {
        fun onItemSelected(item: Note)
        fun onClickDelete(item: Note)
    }



}