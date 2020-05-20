package com.example.make_eat_easy.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.make_eat_easy.R
import com.example.make_eat_easy.adapters.DishesForActionAdapter
import com.example.make_eat_easy.databinding.FragmentAddActionBinding
import com.example.make_eat_easy.viewmodels.AddActionViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddAction : Fragment() {

    private lateinit var binding: FragmentAddActionBinding
    private lateinit var viewModel: AddActionViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_action, container, false)
        val view = binding.root


        viewModel = ViewModelProvider(this)[AddActionViewModel::class.java]

        binding.cookingRadio.setOnClickListener {
            viewModel.actionType = viewModel.COOKING_TYPE
        }

        binding.eatingRadio.setOnClickListener {
            viewModel.actionType = viewModel.EATING_TYPE
        }

        binding.otherRadio.setOnClickListener {
            viewModel.actionType = viewModel.OTHER_TYPE
        }

        binding.timeToStartEdit.setOnClickListener { showDialogs() }

        val adapter = DishesForActionAdapter(requireContext(), viewModel)

        val recyclerView = binding.actionDishes
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        binding.addActionDishesButton.setOnClickListener {
            adapter.itemsCount++
            adapter.notifyDataSetChanged()
        }


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun showDialogs() {
        val calendar = Calendar.getInstance()

        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                TimePickerDialog(
                    requireContext(),
                    TimePickerDialog.OnTimeSetListener { _, hour, minutes ->

                        val dateFormat = SimpleDateFormat("yy-MM-dd HH:mm", Locale.ENGLISH)
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, day)
                        calendar.set(Calendar.HOUR_OF_DAY, hour)
                        calendar.set(Calendar.MINUTE, minutes)
                        Log.d("asdasd", calendar.toString())
                        binding
                            .timeToStartEdit
                            .setText(
                                dateFormat
                                    .format(
                                        calendar.time
                                    )
                            )

                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

}
