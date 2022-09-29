package com.example.noteit.homescreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteit.R
import com.example.noteit.data.NotesDataClass

class HomeScreenRecyclerAdapter(val notes: List<NotesDataClass>):RecyclerView.Adapter<HomeScreenRecyclerAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note,parent,false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        var myList = notes[position]
        holder.textTitle.text = myList.title
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NotesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var textTitle = itemView.findViewById<TextView>(R.id.text_item_title)
    }

}