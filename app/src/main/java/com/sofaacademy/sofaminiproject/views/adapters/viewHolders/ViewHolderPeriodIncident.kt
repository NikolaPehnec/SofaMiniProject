package com.sofaacademy.sofaminiproject.views.adapters.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sofaacademy.sofaminiproject.databinding.GoalIncidentRowBinding
import com.sofaacademy.sofaminiproject.databinding.PeriodRowBinding
import com.sofaacademy.sofaminiproject.model.GoalScoringTeam
import com.sofaacademy.sofaminiproject.model.Incident

class ViewHolderPeriodIncident(private val binding: PeriodRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(periodIncident: Incident.PeriodIncident) {
        binding.periodTimeScore.text = periodIncident.text
    }
}