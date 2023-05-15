package com.sofaacademy.sofaminiproject.utils

import androidx.recyclerview.widget.DiffUtil
import com.sofaacademy.sofaminiproject.model.Player

class PlayersDiffUtilCallback(
    private val oldList: List<Player>,
    private val newList: List<Player>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.position == newItem.position &&
            oldItem.country == newItem.country && oldItem.slug == newItem.slug
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
