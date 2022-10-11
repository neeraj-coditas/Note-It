package com.example.noteit.homescreen.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteit.R
import com.example.noteit.databinding.SingleNoteViewBinding
import com.example.noteit.model.Note

class HomeScreenRecyclerAdapter(private val interaction: Interaction) :
    RecyclerView.Adapter<HomeScreenRecyclerAdapter.NotesViewHolder>() {

    val allNotes = ArrayList<Note>()
    lateinit var mcontext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding =
            SingleNoteViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mcontext = parent.context
        return NotesViewHolder(binding, interaction, mcontext)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(allNotes[position])
    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        Log.d("ListCheckAtAdapter", newList.toString())
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    class NotesViewHolder(
        val binding: SingleNoteViewBinding,
        private val interaction: Interaction,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
            binding.notesDataClass = item

            assignColor()

            itemView.setOnClickListener {
                interaction.onItemSelected(item)
            }

            itemView.setOnLongClickListener {
                binding.apply {
                    viewUnitTvTitle.visibility = View.INVISIBLE
                    singleNoteIvDelete.visibility = View.VISIBLE
                    singleNote.setBackgroundResource(R.drawable.delete_note_shape)
                    singleNote.setOnClickListener {
                        interaction.onClickDelete(item)
                        singleNote.setBackgroundResource(R.drawable.single_note_shape)
                        singleNoteIvDelete.visibility = View.GONE
                        viewUnitTvTitle.visibility = View.VISIBLE
                    }
                }
                true
            }

        }

        fun assignColor() {
            //val noteColor = listOf("#FD99FF","#FF9E9E","#91F48F","#FFF599","#9EFFFF","#B69CFF")
            //holder.itemView.setBackgroundColor(Color.parseColor(random))
            val colorArray = context.resources.getIntArray(R.array.rainbow)
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