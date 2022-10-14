package com.example.noteit.ui.searchscreen.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteit.databinding.SingleNoteViewBinding
import com.example.noteit.data.Note

class SearchRecyclerAdapter(private val interaction: Interaction) :
    RecyclerView.Adapter<SearchRecyclerAdapter.NotesViewHolder>() {

    private val allNotes = ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding =
            SingleNoteViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(allNotes[position])
        assignColor(holder)
    }

    private fun assignColor(holder: NotesViewHolder) {
        val background = holder.itemView.background
        background.setTint(Color.parseColor("#FF000000"))
    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        Log.d("ListCheckAtAdapter", newList.toString())
        notifyItemRangeChanged(0, newList.size + 1)

    }

    override fun getItemCount(): Int = allNotes.size


    class NotesViewHolder(
        private val binding: SingleNoteViewBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
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