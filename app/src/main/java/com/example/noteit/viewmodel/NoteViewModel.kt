package com.example.noteit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteit.data.Note
import com.example.noteit.db.NoteDatabase
import com.example.noteit.db.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = NoteDatabase.getDatabase(application).getNoteDao()
    private val noteRepository = NoteRepository(dao)
    var allNotes: LiveData<List<Note>> = noteRepository.allNotes


    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insert(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.delete(note)
        }
    }

    fun updateNote(note: Note) {
        noteRepository.update(note)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Note>> =
        noteRepository.searchDatabase("%$searchQuery%")
}