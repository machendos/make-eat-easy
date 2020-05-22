package com.example.make_eat_easy.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.alamkanak.weekview.WeekViewEvent
import com.example.make_eat_easy.R
import com.example.make_eat_easy.databinding.FragmentHomeBinding
import com.example.make_eat_easy.viewmodels.AddActionViewModel
import java.util.*

class HomeNavigationFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)
        val viewModel = ViewModelProvider(this)[AddActionViewModel::class.java]

        viewModel.actions.observe(viewLifecycleOwner) { actions ->

            val list = mutableListOf<WeekViewEvent<Event>>()

            actions.forEach {

                val color1 = ContextCompat.getColor(requireContext(), R.color.colorAccent)

                val start = Calendar.getInstance().apply {
                    set(Calendar.YEAR, it.startYear)
                    set(Calendar.MONTH, it.startMonth)
                    set(Calendar.DAY_OF_MONTH, it.srartDat)
                    set(Calendar.HOUR_OF_DAY, it.startHour)
                    set(Calendar.MINUTE, it.startMinute)
                }

                val finish = Calendar.getInstance().apply {
                    set(Calendar.YEAR, it.startYear)
                    set(Calendar.MONTH, it.startMonth)
                    set(Calendar.DAY_OF_MONTH, it.srartDat)
                    set(Calendar.HOUR_OF_DAY, it.startHour)
                    set(Calendar.MINUTE, it.startMinute)
                }

                finish.add(Calendar.MINUTE, it.duration)

                val event = Event(
                    it.actionId.toLong(), "title",

                    start, finish,
                    "123", color1, false, false
                )

//
                    list.add(event.toWeekViewEvent())

            }
            if (list.size > 0)
            binding.weekView.submit(list)

        }

        return binding.root
    }
}
