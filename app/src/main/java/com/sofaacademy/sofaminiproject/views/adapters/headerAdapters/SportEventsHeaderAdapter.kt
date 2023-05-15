package com.sofaacademy.sofaminiproject.views.adapters.headerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.EventHeaderBinding
import com.sofaacademy.sofaminiproject.views.adapters.viewHolders.EventHeaderViewHolder
import java.time.LocalDate

class SportEventsHeaderAdapter(var date: LocalDate?, var eventSize: String?) :
    RecyclerView.Adapter<EventHeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHeaderViewHolder {
        return EventHeaderViewHolder(
            EventHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventHeaderViewHolder, position: Int) {
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
}
