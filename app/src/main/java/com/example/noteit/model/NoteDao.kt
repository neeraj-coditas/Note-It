package com.example.noteit.model

import android.icu.text.CaseMap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("Select * from notes_table order by `Note Time` DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE `Note Title` LIKE :searchQuery OR `Note Description` LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Note>>

}
