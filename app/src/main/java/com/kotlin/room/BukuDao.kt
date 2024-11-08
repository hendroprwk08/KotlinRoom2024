package com.kotlin.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BukuDao {
    @Query("SELECT * FROM buku")
    fun getAllBooks():List<Buku>

    @Query("SELECT * FROM buku WHERE id = :bukuId")
    fun bookById(bukuId: Int):List<Buku>

    @Insert
    fun insert(vararg buku: Buku)

    @Query("DELETE FROM buku")
    fun delete()

    @Query("DELETE FROM buku WHERE id = :bukuId")
    fun deleteById(bukuId: Int)

    @Query("UPDATE buku SET judul = :judul, penulis = :penulis WHERE id = :id")
    fun update(id: Int, judul: String?, penulis: String?): Int
}