package com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.PlayerTeamSearchViewBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.utils.Constants.TYPE_PLAYER
import com.sofaacademy.sofaminiproject.utils.Constants.TYPE_TEAM
import com.sofaacademy.sofaminiproject.utils.TeamPlayerDiffUtilCallback
import com.sofaacademy.sofaminiproject.utils.listeners.OnPlayerClicked
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderSearch

class SearchArrayAdapter(
    private var items: MutableList<Any>,
    private val listenerPlayer: OnPlayerClicked,
    private val listenerTeam: OnTeamClicked,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PLAYER, TYPE_TEAM -> ViewHolderSearch(
                PlayerTeamSearchViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listenerPlayer,
                listenerTeam
            )

            else -> {
                throw java.lang.IllegalArgumentException()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderSearch -> holder.bind(items[position], context)
        }
    }

    fun setItems(newItems: List<Any>) {
        val diffResult = DiffUtil.calculateDiff(TeamPlayerDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Team2 -> TYPE_TEAM
            is Player -> TYPE_PLAYER
            else -> -1
        }
    }

    override fun getItemCount(): Int = items.size
}
