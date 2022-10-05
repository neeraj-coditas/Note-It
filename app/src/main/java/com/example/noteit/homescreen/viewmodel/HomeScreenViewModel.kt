package com.example.noteit.homescreen.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteit.model.Note
import com.example.noteit.model.NoteDao
import com.example.noteit.model.NoteDatabase
import com.example.noteit.model.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application) : AndroidViewModel(application){

    val dao = NoteDatabase.getDatabase(application).getNoteDao()
    val repository = NoteRepository(dao)
    val allNotes: LiveData<List<Note>> = repository.allNotes

    /*init{
        allNotes = repository.allNotes
    }*/

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(note)
        }
    }

}