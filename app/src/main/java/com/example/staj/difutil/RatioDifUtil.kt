package com.example.staj.difutil

import androidx.recyclerview.widget.DiffUtil
import com.example.staj.view.bigmodel.Mainmodel



class RatioDifUtil(private val oldList: List<Mainmodel>, private val newList: List<Mainmodel>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
