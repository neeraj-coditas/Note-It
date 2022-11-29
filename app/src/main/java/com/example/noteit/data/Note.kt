package com.example.noteit.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
class Note(
    @ColumnInfo(name = "Note Title") var title: String,
    @ColumnInfo(name = "Note Description") var description: String,
    @ColumnInfo(name = "Note Time") var timeStamp : Long
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id = 0
}

const val TABLE_NAME = "notes_table"