package com.example.noteit.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes_table")
class Note(
    @ColumnInfo(name = "Note Title") var title: String,
    @ColumnInfo(name = "Note Description") var description: String
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}