package com.example.staj.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.R
import com.example.staj.view.bigmodel.Mainmodel
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(private val matchList:List<Mainmodel>):RecyclerView.Adapter<EventAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(matchList[position])
    }

    override fun getItemCount(): Int=matchList.size


    class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val matchText:TextView=view.findViewById(R.id.matches)
        val dateText:TextView=view.findViewById(R.id.dates)
        val ratio1:TextView=view.findViewById(R.id.ratio1)
        val ratiox:TextView=view.findViewById(R.id.ratioX)
        val ratio2:TextView=view.findViewById(R.id.ratio2)
        val upper:TextView=view.findViewById(R.id.plus)
        val lower:TextView=view.findViewById(R.id.minus)


        fun bindItems(item: Mainmodel){
            matchText.text= item.matchTag.name
            dateText.text=dateFormat(item.matchTag.date)
            ratio1.text=item.finalRatio.ratio1
            ratiox.text=item.finalRatio.ratiox
            ratio2.text=item.finalRatio.ratio2
            upper.text=item.exclusiveRatio.upper
            lower.text=item.exclusiveRatio.lower

        }

        fun dateFormat(date:Long):String{
            return SimpleDateFormat("dd MMM yyyy HH:mm ", Locale("TR","tr")).format(date)
        }

    }

}
