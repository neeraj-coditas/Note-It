package com.example.noteit.homescreen.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.red
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.example.noteit.MainActivity
import com.example.noteit.R
import com.example.noteit.databinding.SingleNoteViewBinding
import com.example.noteit.model.Note
import kotlin.coroutines.coroutineContext

class HomeScreenRecyclerAdapter(private val interaction: Interaction) : RecyclerView.Adapter<HomeScreenRecyclerAdapter.NotesViewHolder>() {

    val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = SingleNoteViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesViewHolder(binding,interaction)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(item = allNotes[position])
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
                interaction.onItemSelected(adapterPosition,item)
            }
            itemView.setOnLongClickListener {
                binding.apply {
                    viewUnitTvTitle.visibility = View.INVISIBLE
                    singleNoteIvDelete.visibility = View.VISIBLE
                    singleNote.setBackgroundResource(R.drawable.delete_note_shape)
                    singleNote.setOnClickListener{
                        interaction.onItemLongClicked(adapterPosition,item)
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
        fun onItemSelected(position: Int, item: Note)
        fun onItemLongClicked( position: Int, item: Note)
    }



}