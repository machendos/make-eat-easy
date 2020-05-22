package com.example.make_eat_easy.util

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.alamkanak.weekview.WeekViewEvent
import com.example.make_eat_easy.R
import com.example.make_eat_easy.databinding.FragmentHomeBinding
import com.example.make_eat_easy.viewmodels.AddActionViewModel
import com.example.make_eat_easy.views.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class HomeNavigationFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater(context).inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.day_1_settings -> binding.weekView.numberOfVisibleDays = 1
            R.id.days_3_settings -> binding.weekView.numberOfVisibleDays = 3
            R.id.days_7_settings -> binding.weekView.numberOfVisibleDays = 7
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity)
            .setActionBarTitle("Make eat easy")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val viewModel = ViewModelProvider(this)[AddActionViewModel::class.java]

        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { _ ->
            findNavController().navigate(R.id.addAction)

        }

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
