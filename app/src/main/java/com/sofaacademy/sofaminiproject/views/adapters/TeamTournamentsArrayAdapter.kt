package com.sofaacademy.sofaminiproject.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.EventHeaderBinding
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.dateLongFormat
import java.time.LocalDate

class TeamTournamentsArrayAdapter(val context: Context, var date: LocalDate?, var eventSize: String?) :
    RecyclerView.Adapter<TeamTournamentsArrayAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(
            EventHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(date, eventSize)
    }

    override fun getItemCount(): Int {
        return 1
    }

    fun setHeaderInfo(newDate: LocalDate?, size: String?) {
        newDate?.let {
            date = newDate
        }
        size?.let {
            eventSize = size
        }
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(private val binding: EventHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(date: LocalDate?, eventSize: String?) {
            date?.let {
                binding.date.text = dateLongFormat.format(date)
            }
            eventSize?.let {
                binding.numOfEvents.text =
                    context.getString(R.string.events_num, eventSize)
            }
        }
    }
}
