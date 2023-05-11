package com.sofaacademy.sofaminiproject.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.MatchRowBinding
import com.sofaacademy.sofaminiproject.databinding.TournamentRowBinding
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants.TYPE_SPORT_EVENT
import com.sofaacademy.sofaminiproject.utils.Constants.TYPE_TOURNAMENT
import com.sofaacademy.sofaminiproject.utils.EventDiffUtilCallback
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getCurrentStatusColor
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeamColorBasedOnTimeAndResult
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeamScoreColorBasedOnTimeAndResult

class SportEventsArrayAdapter(
    private val context: Context,
    private var items: MutableList<Any>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SPORT_EVENT -> ViewHolderEvent(
                MatchRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            TYPE_TOURNAMENT -> ViewHolderTournament(
                TournamentRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> {
                throw java.lang.IllegalArgumentException()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderEvent -> {
                val nextItem = try {
                    items[position + 1]
                } catch (e: java.lang.IndexOutOfBoundsException) {
                    null
                }
                holder.bind(items[position] as SportEvent, nextItem)
            }

            is ViewHolderTournament -> holder.bind(items[position] as Tournament)
        }
    }

    fun getNumberOfItems(): Int = items.size

    fun setItems(newItems: List<Any>) {
        val diffResult = DiffUtil.calculateDiff(EventDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is SportEvent -> TYPE_SPORT_EVENT
            is Tournament -> TYPE_TOURNAMENT
            else -> -1
        }
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(item: Any)
    }

    inner class ViewHolderEvent(private val binding: MatchRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SportEvent, nextItem: Any?) {
            binding.matchItem.apply {
                setHomeTeamColor(
                    getTeamColorBasedOnTimeAndResult(
                        item.status,
                        item.homeScore,
                        item.awayScore,
                        context
                    )
                )
                setAwayTeamColor(
                    getTeamColorBasedOnTimeAndResult(
                        item.status,
                        item.awayScore,
                        item.homeScore,
                        context
                    )
                )
                setHomeTeamScoreColor(
                    getTeamScoreColorBasedOnTimeAndResult(
                        item.status,
                        item.homeScore,
                        item.awayScore,
                        context
                    )
                )
                setAwayTeamScoreColor(
                    getTeamScoreColorBasedOnTimeAndResult(
                        item.status,
                        item.awayScore,
                        item.homeScore,
                        context
                    )
                )
                setCurrentTimeColor(getCurrentStatusColor(item.status, context))
                setMatchInfo(item)
            }
            binding.separatorImg.visibility = GONE
            nextItem?.let {
                if (it is Tournament) {
                    binding.separatorImg.visibility = VISIBLE
                }
            }

            binding.matchItem.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    inner class ViewHolderTournament(private val binding: TournamentRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Tournament) {
            binding.tournamentItem.setTournamentInfo(item)
            binding.tournamentItem.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }
}
