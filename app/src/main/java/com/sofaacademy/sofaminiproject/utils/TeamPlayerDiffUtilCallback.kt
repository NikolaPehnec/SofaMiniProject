package com.sofaacademy.sofaminiproject.utils

import androidx.recyclerview.widget.DiffUtil
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Team2

class TeamPlayerDiffUtilCallback(
    private val oldList: List<Any>,
    private val newList: List<Any>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem is Team2 && newItem is Team2) {
            return oldItem.id == newItem.id
        }
        if (oldItem is Player && newItem is Player) {
            return oldItem.id == newItem.id
        }
        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is Team2 && newItem is Team2) {
            return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.country == newItem.country && oldItem.sport == newItem.sport
        }
        if (oldItem is Player && newItem is Player) {
            return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.country == newItem.country && oldItem.position == newItem.position && oldItem.slug == newItem.slug &&
                oldItem.sport == newItem.sport
        }

        return false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
