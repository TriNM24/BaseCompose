package com.example.composeexercise.ui.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey()
    @ColumnInfo(name = "word")
    var word: String
)
