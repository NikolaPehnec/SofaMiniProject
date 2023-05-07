package com.sofaacademy.sofaminiproject.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.MatchRowBinding
import com.sofaacademy.sofaminiproject.databinding.TournamentRowBinding
import com.sofaacademy.sofaminiproject.model.MatchStatus
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.EventDiffUtilCallback
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions

class SportEventsArrayAdapter(
    private val context: Context,
    private var items: MutableList<Any>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> ViewHolderEvent(
                MatchRowBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            2 -> ViewHolderTournament(
                TournamentRowBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
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
            is SportEvent -> 1
            is Tournament -> 2
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

            binding.matchItem.loadTeamLogos(item.homeTeam.id, item.awayTeam.id)
            binding.matchItem.setHomeTeamColor(
                getTeamColorBasedOnTimeAndResult(
                    item.status, homeResult, awayResult
                )
            )
            binding.matchItem.setHomeTeamScoreColor(
                getTeamScoreColorBasedOnTimeAndResult(
                    item.status,
                    homeResult,
                    awayResult
                )
            )
            binding.matchItem.setAwayTeamColor(
                getTeamColorBasedOnTimeAndResult(
                    item.status, awayResult, homeResult
                )
            )
            binding.matchItem.setAwayTeamScoreColor(
                getTeamColorBasedOnTimeAndResult(
                    item.status, awayResult, homeResult
                )
            )
            binding.matchItem.setCurrentTimeColor(getCurrentStatusColor(item.status))
            binding.matchItem.setMatchInfo(
                startTime,
                matchCurrentStatus,
                item.homeTeam.name,
                item.awayTeam.name,
                homeResult,
                awayResult
            )
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

    /**
     * Za home i away score na docsima pise da su object tipa Score,
     * ali kad nema podataka onda je prazna lista, primjer:
     * "homeScore": [], ili
     * "homeScore": {"total": 4,"period1": 2,"period2": 2}, ne moze biti ni score ni lista scorea
     */
    private fun getCurrentMatchStatus(status: String, matchStartTime: String): String {
        return when (status) {
            MatchStatus.NOT_STARTED.status -> "-"
            MatchStatus.FINISHED.status -> "FT"
            MatchStatus.IN_PROGRESS.status -> {
                UtilityFunctions.elapsedMinutesFromDate(matchStartTime) + "'"
            }
            else -> ""
        }
    }

    private fun getResultValue(score: Any?): String {
        return when (score) {
            is Map<*, *> -> {
                (score["total"] as? Double)?.toInt().toString()
            }
            else -> ""
        }
    }

    private fun getTeamColorBasedOnTimeAndResult(
        matchStatus: String, teamResult: String, otherTeamResult: String
    ): Int {
        return when (matchStatus) {
            MatchStatus.FINISHED.status -> {
                if (teamResult.toInt() > otherTeamResult.toInt()) context.getColor(R.color.on_surface_on_surface_lv_1)
                else context.getColor(R.color.on_surface_on_surface_lv_2)
            }
            else -> context.getColor(R.color.on_surface_on_surface_lv_1)
        }
    }

    private fun getCurrentStatusColor(
        matchStatus: String
    ): Int {
        return when (matchStatus) {
            MatchStatus.IN_PROGRESS.status -> context.getColor(R.color.specific_live)
            else -> context.getColor(R.color.on_surface_on_surface_lv_2)
        }
    }

    private fun getTeamScoreColorBasedOnTimeAndResult(
        matchStatus: String, teamResult: String, otherTeamResult: String
    ): Int {
        return when (matchStatus) {
            MatchStatus.FINISHED.status -> {
                getTeamColorBasedOnTimeAndResult(matchStatus, teamResult, otherTeamResult)
            }
            MatchStatus.IN_PROGRESS.status -> context.getColor(R.color.specific_live)
            else -> context.getColor(R.color.on_surface_on_surface_lv_1)
        }
    }

}