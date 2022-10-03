package com.example.noteit.homescreen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.noteit.data.Note

class HomeScreenViewModel : ViewModel(){

    val notesObjects = mutableListOf<Note>()

    init {
        notesObjects.add(Note("Reading List"))
        notesObjects.add(Note("TO DO"))
        notesObjects.add(Note("Shopping List"))
        notesObjects.add(Note("Grocery List"))
        notesObjects.add(Note("Recipe List"))
        notesObjects.add(Note("Workout List"))
        notesObjects.add(Note("Songs List"))
        notesObjects.add(Note("Movie List"))
        notesObjects.add(Note("Cafe List"))
        notesObjects.add(Note("Restaurants List"))
        notesObjects.add(Note("Cricket Teams List"))
        notesObjects.add(Note("Football Teams List"))
        notesObjects.add(Note("Football Teams List"))
        notesObjects.add(Note("Football Teams List"))
        notesObjects.add(Note("Football Teams List"))
        notesObjects.add(Note("Football Teams List"))
        notesObjects.add(Note("Football Teams List"))
        notesObjects.add(Note("Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List"))
    }
}