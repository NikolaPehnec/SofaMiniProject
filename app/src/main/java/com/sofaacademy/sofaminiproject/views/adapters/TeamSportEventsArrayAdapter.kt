package com.sofaacademy.sofaminiproject.views.adapters

import android.view.LayoutInflater
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
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.elapsedMinutesBetweenTwoDates
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.elapsedMinutesFromDate
import com.sofaacademy.sofaminiproject.utils.listeners.OnEventClicked
import com.sofaacademy.sofaminiproject.utils.listeners.OnTournamentClicked
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderEvent
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderTournament
import java.time.LocalDateTime

class TeamSportEventsArrayAdapter(
    private var items: MutableList<Any>,
    private val listenerTournament: OnTournamentClicked,
    private val listenerEvent: OnEventClicked
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SPORT_EVENT -> ViewHolderEvent(
                MatchRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listenerEvent
            )

            TYPE_TOURNAMENT -> ViewHolderTournament(
                TournamentRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listenerTournament
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
                holder.bind(items[position] as SportEvent, nextItem, true)
            }

            is ViewHolderTournament -> holder.bind(items[position] as Tournament)
        }
    }

    fun setItems(newItems: List<Any>) {
        val diffResult = DiffUtil.calculateDiff(EventDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    fun findNextEventPosition(): Int {
        var indexOfNextEvent = -1

        if (items.isNotEmpty()) {
            var nextEvent = items.filter { e -> e is SportEvent }.first() as SportEvent
            val today = LocalDateTime.now()
            var timeToNextEvent = 100000L

            items.forEach { item ->
                if (item is SportEvent) {
                    var timeToEvent =
                        elapsedMinutesBetweenTwoDates(today, item.startDate.toString())
                    if (timeToEvent in 1 until timeToNextEvent) {
                        nextEvent = item
                        timeToNextEvent = timeToEvent
                        indexOfNextEvent = items.indexOf(item)
                    }
                }
            }
            //If previous item is tournament scroll to tournament
            try {
                if (items[indexOfNextEvent - 1] is Tournament) {
                    return indexOfNextEvent - 1
                }
            } catch (e: Exception) {
            }
        }

        return indexOfNextEvent
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is SportEvent -> TYPE_SPORT_EVENT
            is Tournament -> TYPE_TOURNAMENT
            else -> -1
        }
    }

    override fun getItemCount(): Int = items.size
}
