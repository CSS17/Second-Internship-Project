package com.example.staj.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.R
import com.example.staj.models.EventItem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(private val list: List<EventItem>):RecyclerView.Adapter<EventAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    override fun getItemCount(): Int=list.size


    class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val matchText:TextView=view.findViewById(R.id.matches)
        val dateText:TextView=view.findViewById(R.id.dates)

        fun bindItems(event: EventItem){
            matchText.text= event.name
            dateText.text=dateFormat(event.date)
        }

        fun dateFormat(date:Long):String{
            return SimpleDateFormat("dd MMM yyyy HH:mm ", Locale("TR","tr")).format(date)
        }

    }

}