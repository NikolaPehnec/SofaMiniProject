package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.EventHeaderBinding
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions
import java.time.LocalDate

class EventHeaderViewHolder(private val binding: EventHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(date: LocalDate?, eventSize: String?) {
        date?.let {
            binding.date.text = UtilityFunctions.dateLongFormat.format(date)
        }
        eventSize?.let {
            binding.numOfEvents.text = eventSize
        }
    }
}
