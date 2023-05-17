package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.EventHeaderBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventHeaderViewHolder(
    private val binding: EventHeaderBinding,
    private val dateLongFormatter: DateTimeFormatter
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(date: LocalDate?, eventSize: String?) {
        date?.let {
            binding.date.text = dateLongFormatter.format(date)
        }
        eventSize?.let {
            binding.numOfEvents.text = eventSize
        }
    }
}
