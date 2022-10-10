package com.example.noteit.homescreen.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteit.model.Note
import com.example.noteit.model.NoteDao
import com.example.noteit.model.NoteDatabase
import com.example.noteit.model.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application) : AndroidViewModel(application){

    private val dao = NoteDatabase.getDatabase(application).getNoteDao()
    private val noteRepository = NoteRepository(dao)
    val allNotes: LiveData<List<Note>> = noteRepository.allNotes

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insert(note)
        }
    }

    fun deleteNote(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.delete(note)
        }
    }

    fun updateNote(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.update(note)
        }
    }

    fun updatenote(id: Int, title: String, description : String){
        viewModelScope.launch {
            noteRepository.updateNote(id, title, description)
        }
    }

}