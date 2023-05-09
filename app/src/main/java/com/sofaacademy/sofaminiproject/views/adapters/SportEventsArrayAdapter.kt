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
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getCurrentMatchStatus
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getCurrentStatusColor
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getResultValue
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getTeamColorBasedOnTimeAndResult
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getTeamScoreColorBasedOnTimeAndResult

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
            val startTime = UtilityFunctions.getHourFromDate(item.startDate!!)
            val matchCurrentStatus = getCurrentMatchStatus(item.status, item.startDate)
            val homeResult = getResultValue(item.homeScore)
            val awayResult = getResultValue(item.awayScore)

            binding.matchItem.apply {
                loadTeamLogos(item.homeTeam.id, item.awayTeam.id)
                setHomeTeamColor(
                    getTeamColorBasedOnTimeAndResult(
                        item.status,
                        homeResult,
                        awayResult,
                        context
                    )
                )
                setAwayTeamColor(
                    getTeamColorBasedOnTimeAndResult(
                        item.status,
                        awayResult,
                        homeResult,
                        context
                    )
                )
                setHomeTeamScoreColor(
                    getTeamScoreColorBasedOnTimeAndResult(
                        item.status,
                        homeResult,
                        awayResult,
                        context
                    )
                )
                setAwayTeamScoreColor(
                    getTeamScoreColorBasedOnTimeAndResult(
                        item.status,
                        awayResult,
                        homeResult,
                        context
                    )
                )
                setCurrentTimeColor(getCurrentStatusColor(item.status, context))
                setMatchInfo(
                    startTime,
                    matchCurrentStatus,
                    item.homeTeam.name,
                    item.awayTeam.name,
                    homeResult,
                    awayResult
                )
            }
            binding.separatorImg.visibility = GONE
            nextItem?.let {
                if (it is Tournament) {
                    binding.separatorImg.visibility = VISIBLE
                }
            }
        }
    }

    inner class ViewHolderTournament(private val binding: TournamentRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Tournament) {
            binding.tournamentItem.setTournamentInfo(item.name, item.country.name)
            binding.tournamentItem.loadTournamentLogo(item.id)
        }
    }
}
