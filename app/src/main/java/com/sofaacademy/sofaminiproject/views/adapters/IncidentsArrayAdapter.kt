package com.sofaacademy.sofaminiproject.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.GoalIncidentRowBinding
import com.sofaacademy.sofaminiproject.model.Incident
import com.sofaacademy.sofaminiproject.model.IncidentEnum
import com.sofaacademy.sofaminiproject.utils.EventDiffUtilCallback
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.ViewHolderGoalIncident

class IncidentsArrayAdapter(
    private val context: Context,
    private var items: MutableList<Any>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            IncidentEnum.TYPE_GOAL.ordinal -> ViewHolderGoalIncident(
                GoalIncidentRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            /*  IncidentEnum.TYPE_CARD.ordinal -> ViewHolderTournament(
                  TournamentRowBinding.inflate(
                      LayoutInflater.from(parent.context),
                      parent,
                      false
                  )
              )

              IncidentEnum.TYPE_PERIOD.ordinal -> ViewHolderTournament(
                  TournamentRowBinding.inflate(
                      LayoutInflater.from(parent.context),
                      parent,
                      false
                  )
              )*/

            else -> {
                ViewHolderGoalIncident(
                    GoalIncidentRowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                //throw IllegalArgumentException("Nikad ne izvrseno")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderGoalIncident -> {
                //Poslije maknuti
                if (items[position] is Incident.GoalIncident)
                    holder.bind(items[position] as Incident.GoalIncident)
            }

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
            is Incident.GoalIncident -> IncidentEnum.TYPE_GOAL.ordinal
            is Incident.CardIncident -> IncidentEnum.TYPE_CARD.ordinal
            is Incident.PeriodIncident -> IncidentEnum.TYPE_PERIOD.ordinal
            else -> throw IllegalArgumentException("Invalid View")
        }
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(item: Any)
    }

}
