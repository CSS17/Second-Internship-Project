package com.example.staj.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.R
import com.example.staj.viewmodel.Matches

class MyAdapter(private val matchList: ArrayList<Matches>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=matchList[position]
        holder.dateTitle.text=currentItem.date
        holder.matchTitle.text=currentItem.match
    }

    override fun getItemCount(): Int {
        return matchList.size
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val matchTitle:TextView=itemView.findViewById(R.id.matches)
            val dateTitle:TextView=itemView.findViewById(R.id.dates)
    }
}