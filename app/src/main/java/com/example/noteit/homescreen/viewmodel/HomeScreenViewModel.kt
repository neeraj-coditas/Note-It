package com.example.noteit.homescreen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.noteit.data.NotesDataClass

class HomeScreenViewModel : ViewModel(){

    val notesObjects = mutableListOf<NotesDataClass>()

    init {
        TODO("Dummy Data to be deleted later")
        notesObjects.add(NotesDataClass("Reading List"))
        notesObjects.add(NotesDataClass("TO DO"))
        notesObjects.add(NotesDataClass("Shopping List"))
        notesObjects.add(NotesDataClass("Grocery List"))
        notesObjects.add(NotesDataClass("Recipe List"))
        notesObjects.add(NotesDataClass("Workout List"))
        notesObjects.add(NotesDataClass("Songs List"))
        notesObjects.add(NotesDataClass("Movie List"))
        notesObjects.add(NotesDataClass("Cafe List"))
        notesObjects.add(NotesDataClass("Restaurants List"))
        notesObjects.add(NotesDataClass("Cricket Teams List"))
        notesObjects.add(NotesDataClass("Football Teams List"))
        notesObjects.add(NotesDataClass("Football Teams List"))
        notesObjects.add(NotesDataClass("Football Teams List"))
        notesObjects.add(NotesDataClass("Football Teams List"))
        notesObjects.add(NotesDataClass("Football Teams List"))
        notesObjects.add(NotesDataClass("Football Teams List"))
        notesObjects.add(NotesDataClass("Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List Football Teams List"))
    }
}