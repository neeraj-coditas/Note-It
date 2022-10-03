package com.example.noteit.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes_table")
class Note(@ColumnInfo(name = "Note Title") val title: String) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}