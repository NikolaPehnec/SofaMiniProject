package com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.MatchRowBinding
import com.sofaacademy.sofaminiproject.databinding.TournamentMatchHeaderBinding
import com.sofaacademy.sofaminiproject.databinding.TournamentRowBinding
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.EventComparator
import com.sofaacademy.sofaminiproject.utils.listeners.OnEventClicked
import com.sofaacademy.sofaminiproject.utils.listeners.OnTournamentClicked
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.TournamentRoundHeaderViewHolder
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderEvent
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderTournament

class EventPagingAdapter(
    private val listenerEvent: OnEventClicked,
    private val listenerTournament: OnTournamentClicked
) :
    PagingDataAdapter<Any, RecyclerView.ViewHolder>(EventComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.TYPE_SPORT_EVENT -> ViewHolderEvent(
                MatchRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listenerEvent
            )

            Constants.TYPE_TOURNAMENT -> ViewHolderTournament(
                TournamentRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listenerTournament
            )

            Constants.TYPE_ROUND -> TournamentRoundHeaderViewHolder(
                TournamentMatchHeaderBinding.inflate(
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
                    getItem(position + 1)
                } catch (e: java.lang.IndexOutOfBoundsException) {
                    null
                }
                holder.bind(getItem(position) as SportEvent, nextItem, true)
            }

            is ViewHolderTournament -> holder.bind(getItem(position) as Tournament)
            is TournamentRoundHeaderViewHolder -> holder.bind("Round " + getItem(position).toString())
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SportEvent -> Constants.TYPE_SPORT_EVENT
            is Tournament -> Constants.TYPE_TOURNAMENT
            is Int -> Constants.TYPE_ROUND
            else -> -1
        }
    }
}
