package com.kotlin.room

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kotlin.room.databinding.ActivityDetilBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetilBinding
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //back button
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val bundle = intent.extras
        id = bundle?.getInt("b_id")!!

        //ambil data berdasarkan id
        var buku:List<Buku> = emptyList()

        GlobalScope.launch {
            buku = MainActivity.db.bukuDao().bookById(id)
            binding.etJudul.setText(buku.get(0).judul)
            binding.etPenulis.setText(buku.get(0).penulis)
        }
    }

    override fun onStart() {
        super.onStart()

        binding.btHapus.setOnClickListener(){
            GlobalScope.launch {
                MainActivity.db.bukuDao().deleteById(id)
            }

            tutup()
        }

        binding.btSimpan.setOnClickListener(){
            GlobalScope.launch {
                MainActivity.db.bukuDao().update(id, binding.etJudul.text.toString(), binding.etPenulis.text.toString())
            }

            tutup()
        }
    }

    fun tutup(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}