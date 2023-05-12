package com.sofaacademy.sofaminiproject.utils

import androidx.recyclerview.widget.DiffUtil
import com.sofaacademy.sofaminiproject.model.StandingsRow

class StandingsDiffUtilCallback(
    private val oldList: List<StandingsRow>,
    private val newList: List<StandingsRow>
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

        return oldItem.id == newItem.id && oldItem.draws == newItem.draws && oldItem.played == newItem.played &&
            oldItem.wins == newItem.wins && oldItem.losses == newItem.losses && oldItem.points == newItem.points &&
            oldItem.scoresAgainst == newItem.scoresAgainst && oldItem.scoresFor == newItem.scoresFor
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
