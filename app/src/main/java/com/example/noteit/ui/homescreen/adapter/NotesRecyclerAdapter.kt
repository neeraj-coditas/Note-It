package com.example.noteit.ui.homescreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.noteit.R
import com.example.noteit.databinding.SingleNoteViewBinding
import com.example.noteit.data.Note

class NotesRecyclerAdapter(private val interaction: Interaction) :
    RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>() {

    private val allNotes = ArrayList<Note>()
    private var count = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding =
            SingleNoteViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(allNotes[position])
    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyItemRangeChanged(0, newList.size + 1)
    }

    override fun getItemCount(): Int = allNotes.size

    inner class NotesViewHolder(
        private val binding: SingleNoteViewBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) {
            binding.notesDataClass = item
            assignColor()
            itemView.setOnClickListener {
                interaction.onItemSelected(item)
            }

            itemView.setOnLongClickListener {
                if (count < 0) {
                    binding.apply {
                        viewUnitTvTitle.visibility = View.INVISIBLE
                        singleNoteIvDelete.visibility = View.VISIBLE
                        singleNote.setBackgroundResource(R.drawable.delete_note_shape)

                        singleNote.setOnClickListener {
                            interaction.onClickDelete(item)
                            singleNote.setBackgroundResource(R.drawable.single_note_shape)
                            singleNoteIvDelete.visibility = View.GONE
                            viewUnitTvTitle.visibility = View.VISIBLE
                            count--
                        }
                    }

                } else {
                    Toast.makeText(binding.root.context, "Not Allowed", Toast.LENGTH_SHORT).show()
                }
                true
            }
        }

        private fun assignColor() {
            val colorArray = binding.root.context.resources.getIntArray(R.array.rainbow)
            val random = colorArray.random()
            val background = binding.singleNote.background
            background.setTint(random)
        }
    }

    interface Interaction {
        fun onItemSelected(item: Note)
        fun onClickDelete(item: Note)
    }

}