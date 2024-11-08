package com.kotlin.room

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.kotlin.room.databinding.ActivityMainBinding
import com.kotlin.room.databinding.InputLayoutBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var inputLayoutBinding: InputLayoutBinding //bind dialog layout
    private lateinit var bukuAdapter: BukuAdapter

    companion object { // static behaviour
        lateinit var db: BukuDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // inisialisasi Database
        db = Room.databaseBuilder(applicationContext, BukuDatabase::class.java, "buku-db").build()
    }

    override fun onStart() {
        super.onStart()

        setSampleData()
        Thread.sleep(2000L)
        displayData()

        binding.floatingActionButton.setOnClickListener {
            inputLayoutBinding = InputLayoutBinding.inflate(layoutInflater) //binding

            val builder = AlertDialog.Builder(this)
                .setPositiveButton(R.string.simpan) { dialog, which ->

                    GlobalScope.launch {
                        db.bukuDao().insert(
                            Buku(
                                inputLayoutBinding.etJudul.text.toString(),
                                inputLayoutBinding.etPenulis.text.toString()
                            )
                        )

                        displayData()
                    }
                    // refresh adapter
                    bukuAdapter.notifyDataSetChanged()
                }
                .setNeutralButton(R.string.tidak) { dialog, which ->
                    dialog.cancel()
                }.create()

            builder.setView(inputLayoutBinding.root) //masukkan layout
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }
    }

    fun setSampleData() = GlobalScope.launch {
        if (db.bukuDao().getAllBooks().size == 0) {
            db.bukuDao().insert(Buku("Bumi Manusia", "Pramoedya Ananta Toer"))
            db.bukuDao().insert(Buku("Laskar Pelangi", "Andrea Hirata"))
            db.bukuDao().insert(Buku("Anak Semua Bangsa", "Pramoedya Ananta Toer"))
            db.bukuDao().insert(Buku("Negeri 5 Menara", "Ahmad Fuadi"))
            db.bukuDao().insert(Buku("Daun yang Jatuh Tak Pernah Membenci Angin", "Tere Liye"))
            Log.d("Mine", "Sukses: data awal telah masuk")
        }

        Log.d("Mine", "Selesai: data telah tersedia")
    }

    private fun displayData() = GlobalScope.launch {
        bukuAdapter = BukuAdapter(applicationContext, db.bukuDao().getAllBooks())

        val layoutManager = GridLayoutManager(applicationContext, 1) //jumlah kolom 1
        binding.contentMain.recyclerView.layoutManager = layoutManager
        binding.contentMain.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.contentMain.recyclerView.adapter = bukuAdapter
        Log.d("Mine", "Sukses: menampilkan data")
    }

}