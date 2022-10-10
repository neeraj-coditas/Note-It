package com.example.noteit.model

import android.icu.text.CaseMap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("Update NOTES_TABLE SET `Note Title` = :title , `Note Description` = :description WHERE `id` = :id")
    suspend fun updateNote(id: Int, title : String, description: String)

    @Query("Select * from notes_table order by id DESC")
    fun getAllNotes(): LiveData<List<Note>>
}