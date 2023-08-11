package com.example.staj.difutil

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.staj.view.bigmodel.Mainmodel



class RatioDifUtil(private val oldList: List<Mainmodel>, private val newList: List<Mainmodel>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val isequal= oldList==newList
        val isequal2=oldList[oldItemPosition]==newList[newItemPosition]
        return oldList[oldItemPosition].eventId === newList[newItemPosition].eventId
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }


}
