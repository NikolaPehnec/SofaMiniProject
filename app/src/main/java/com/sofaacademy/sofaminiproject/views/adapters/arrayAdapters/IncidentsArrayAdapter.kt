package com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.CardIncidentRowBinding
import com.sofaacademy.sofaminiproject.databinding.GoalIncidentRowBinding
import com.sofaacademy.sofaminiproject.databinding.PeriodRowBinding
import com.sofaacademy.sofaminiproject.model.Incident
import com.sofaacademy.sofaminiproject.model.IncidentEnum
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.utils.EventDiffUtilCallback
import com.sofaacademy.sofaminiproject.utils.listeners.OnIncidentClicked
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderCardIncident
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderGoalIncident
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderPeriodIncident

class IncidentsArrayAdapter(
    private val sportSlug: String,
    private var items: MutableList<Any>,
    private var sportEvent: SportEvent,
    private var onIncidentClicked: OnIncidentClicked
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            IncidentEnum.TYPE_GOAL.ordinal -> ViewHolderGoalIncident(
                GoalIncidentRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                sportSlug,
                sportEvent
            )

            IncidentEnum.TYPE_CARD.ordinal -> ViewHolderCardIncident(
                CardIncidentRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                sportEvent
            )

            IncidentEnum.TYPE_PERIOD.ordinal -> ViewHolderPeriodIncident(
                PeriodRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Nikad ne izvrseno")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderGoalIncident ->
                holder.bind(items[position] as Incident.GoalIncident, onIncidentClicked)

            is ViewHolderCardIncident ->
                holder.bind(items[position] as Incident.CardIncident, onIncidentClicked)

            is ViewHolderPeriodIncident ->
                holder.bind(items[position] as Incident.PeriodIncident)
        }
    }

    fun setItems(newItems: List<Any>) {
        val diffResult = DiffUtil.calculateDiff(EventDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Incident.GoalIncident -> IncidentEnum.TYPE_GOAL.ordinal
            is Incident.CardIncident -> IncidentEnum.TYPE_CARD.ordinal
            is Incident.PeriodIncident -> IncidentEnum.TYPE_PERIOD.ordinal
            else -> throw IllegalArgumentException("Invalid View")
        }
    }

    override fun getItemCount(): Int = items.size
}
