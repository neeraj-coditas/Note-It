package com.example.noteit.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteit.data.Note

@Database(entities = [Note::class], version = ROOMDB_VERSION, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null


        fun getDatabase(context: Context): NoteDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    ROOMDB_NAME
                ).build()
                INSTANCE = instance

                // return instance
                return instance
            }
        }
    }

}

const val ROOMDB_VERSION = 1
const val ROOMDB_NAME = "note_database"
