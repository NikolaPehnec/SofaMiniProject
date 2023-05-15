package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.PeriodRowBinding
import com.sofaacademy.sofaminiproject.model.Incident
import com.sofaacademy.sofaminiproject.utils.listeners.OnIncidentClicked

class ViewHolderPeriodIncident(private val binding: PeriodRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(periodIncident: Incident.PeriodIncident) {
        binding.periodTimeScore.text = periodIncident.text
    }
}
