package com.sofaacademy.sofaminiproject.utils

import androidx.recyclerview.widget.DiffUtil
import com.sofaacademy.sofaminiproject.model.SearchPlayer
import com.sofaacademy.sofaminiproject.model.SearchTeam

class TeamPlayerDiffUtilCallback(
    private val oldList: List<Any>,
    private val newList: List<Any>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem is SearchTeam && newItem is SearchTeam) {
            return oldItem.id == newItem.id
        }
        if (oldItem is SearchPlayer && newItem is SearchPlayer) {
            return oldItem.id == newItem.id
        }
        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is SearchTeam && newItem is SearchTeam) {
            return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.country == newItem.country && oldItem.sport == newItem.sport
        }
        if (oldItem is SearchPlayer && newItem is SearchPlayer) {
            return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.country == newItem.country && oldItem.position == newItem.position && oldItem.slug == newItem.slug &&
                oldItem.sport == newItem.sport
        }

        return false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
