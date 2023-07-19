package com.example.staj.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.R
import com.example.staj.models.EventItem
import com.example.staj.view.model.ViewItem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(private val list: List<EventItem>,private val ratioList: List<ViewItem>):RecyclerView.Adapter<EventAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(list[position],ratioList[position])
    }

    override fun getItemCount(): Int=list.size


    class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val matchText:TextView=view.findViewById(R.id.matches)
        val dateText:TextView=view.findViewById(R.id.dates)
        val ratio1:TextView=view.findViewById(R.id.ratio1)
        val ratiox:TextView=view.findViewById(R.id.ratioX)
        val ratio2:TextView=view.findViewById(R.id.ratio2)
        val plus:TextView=view.findViewById(R.id.plus)
        val minus:TextView=view.findViewById(R.id.minus)
        
        fun bindItems(event: EventItem,ratio:ViewItem){
            matchText.text= event.name
            dateText.text=dateFormat(event.date)
            ratio1.text=ratio.ratio1
            ratiox.text=ratio.ratiox
            ratio2.text=ratio.ratio2


        }

        fun dateFormat(date:Long):String{
            return SimpleDateFormat("dd MMM yyyy HH:mm ", Locale("TR","tr")).format(date)
        }

    }

}