package com.kotlin.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buku")
data class Buku(
    @ColumnInfo(name = "judul") val judul: String,
    @ColumnInfo(name = "penulis") val penulis: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}