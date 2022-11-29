package com.example.noteit.db

import androidx.lifecycle.LiveData
import com.example.noteit.data.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> by lazy {
        noteDao.getAllNotes()
    }

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Note>> =
        noteDao.searchDatabase(searchQuery)
}