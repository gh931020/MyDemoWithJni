package com.example.mydemowithjni.room

import androidx.room.ColumnInfo

data class NameTuple(
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String
)
