package com.sofaacademy.sofaminiproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sofaacademy.sofaminiproject.databinding.FragmentSlugBinding
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_ARG
import com.sofaacademy.sofaminiproject.viewmodel.SportEventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SlugFragment : Fragment() {
    private var _binding: FragmentSlugBinding? = null
    private val binding get() = _binding!!
    private var slug: String? = null
    private val sportEventViewModel: SportEventViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance(slug: String) =
            SlugFragment().apply {
                arguments = Bundle().apply {
                    putString(SLUG_ARG, slug)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            slug = it.getString(SLUG_ARG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlugBinding.inflate(inflater, container, false)

        setListeners()
        sportEventViewModel.getSportEvents("football","2023-04-29")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.test.text = slug
    }

    private fun setListeners() {
        sportEventViewModel.sportEventsList.observe(viewLifecycleOwner) {
            println(it)
        }
    }


}