package com.example.noteit.homescreen.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteit.model.Note
import com.example.noteit.model.NoteDao
import com.example.noteit.model.NoteDatabase
import com.example.noteit.model.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application) : AndroidViewModel(application){

    private val dao = NoteDatabase.getDatabase(application).getNoteDao()
    private val noteRepository = NoteRepository(dao)
    var allNotes: LiveData<List<Note>> = noteRepository.allNotes

    var readData : LiveData<List<Note>>? = null

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


    fun searchDatabase(searchQuery: String): LiveData<List<Note>>? {
        readData = noteRepository.searchDatabase(searchQuery)
        return readData
    }

}