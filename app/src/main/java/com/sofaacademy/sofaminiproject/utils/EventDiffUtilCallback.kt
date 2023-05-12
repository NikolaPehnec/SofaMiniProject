package com.sofaacademy.sofaminiproject.utils

import androidx.recyclerview.widget.DiffUtil
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Tournament

class EventDiffUtilCallback(
    private val oldList: List<Any>,
    private val newList: List<Any>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem is Tournament && newItem is Tournament) {
            return oldItem.id == newItem.id
        }
        if (oldItem is SportEvent && newItem is SportEvent) {
            return oldItem.id == newItem.id
        }
        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is Tournament && newItem is Tournament) {
            return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.country == newItem.country && oldItem.sport == newItem.sport
        }
        if (oldItem is SportEvent && newItem is SportEvent) {
            return oldItem.id == newItem.id && oldItem.startDate == newItem.startDate && oldItem.awayScore == newItem.awayScore && oldItem.homeScore == newItem.homeScore && oldItem.awayTeam == newItem.awayTeam && oldItem.status == newItem.status
        }

        return false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}

object EventComparator: DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is Tournament && newItem is Tournament) {
            return oldItem.id == newItem.id
        }
        if (oldItem is SportEvent && newItem is SportEvent) {
            return oldItem.id == newItem.id
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is Tournament && newItem is Tournament) {
            return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.country == newItem.country && oldItem.sport == newItem.sport
        }
        if (oldItem is SportEvent && newItem is SportEvent) {
            return oldItem.id == newItem.id && oldItem.startDate == newItem.startDate && oldItem.awayScore == newItem.awayScore && oldItem.homeScore == newItem.homeScore && oldItem.awayTeam == newItem.awayTeam && oldItem.status == newItem.status
        }
        return false
    }
}
