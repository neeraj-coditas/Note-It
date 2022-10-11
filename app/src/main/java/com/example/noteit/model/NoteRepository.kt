package com.example.noteit.model

import android.icu.text.CaseMap
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes : LiveData<List<Note>> = noteDao.getAllNotes()


    suspend fun insert(note:Note){
        noteDao.insert(note)
    }

    suspend fun delete(note:Note){
        noteDao.delete(note)
    }

    suspend fun update(note: Note){
        noteDao.update(note)
    }

    suspend fun updateNote(id: Int, title: String, description : String){
        noteDao.updateNote(id,title,description)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Note>> {
        return noteDao.searchDatabase(searchQuery)
    }

}