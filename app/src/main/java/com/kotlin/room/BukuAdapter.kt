package com.kotlin.room

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.room.databinding.RvItemsBinding

class BukuAdapter (var context: Context, private var bukuList: List<Buku>) :
    RecyclerView.Adapter<BukuAdapter.MyViewHolder>(){

    //binding layout: 1. ganti "binding: ItemRowLayoutBinding" dan "binding.root"
    class MyViewHolder(val binding: RvItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BukuAdapter.MyViewHolder {
        //binding layout: 2. tarik layout
        val binding = RvItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BukuAdapter.MyViewHolder, position: Int) {
        //binding layout: 3. letakkan nilai pada layout
        holder.binding.tvJudul.text = bukuList[position].judul
        holder.binding.tvPenulis.text = bukuList[position].penulis
        val _id: Int? = bukuList[position].id

        holder.binding.btDetil.setOnClickListener(){
            val bundle = Bundle()
            bundle.putInt("b_id", _id!!)

            val intent = Intent(context, DetilActivity::class.java)
            intent.putExtras(bundle)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return bukuList.size
    }
}