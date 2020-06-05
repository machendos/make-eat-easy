package com.example.make_eat_easy.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

    override fun onResume() {
        super.onResume()

//        TODO:
//        (activity as MainActivity)
//            .setActionBarTitle("Add action")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_action, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[AddActionViewModel::class.java]

        binding.cookingRadio.setOnClickListener {
            viewModel.setType(viewModel.COOKING_TYPE)
        }

        binding.eatingRadio.setOnClickListener {
            viewModel.setType(viewModel.EATING_TYPE)
        }

        binding.otherRadio.setOnClickListener {
            viewModel.setType(viewModel.OTHER_TYPE)
        }

        binding.timeToStartEdit.setOnClickListener { showDialogs() }

        val adapter = DishesForActionAdapter(requireContext(), viewModel)

        val recyclerView = binding.actionDishes
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        binding.addActionDishesButton.setOnClickListener {
            adapter.itemsCount++//
//    @Test
//    fun necessaryDishesRecyclerCount() {
//        val scenario = launchFragmentInContainer<AddAction>()
//
//        class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
//            override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
//                if (noViewFoundException != null) {
//                    throw noViewFoundException
//                }
//                val recyclerView = view as RecyclerView
//                val adapter = recyclerView.adapter
//                assertThat(adapter!!.itemCount, `is`(expectedCount))
//            }
//
//        }
//
//        onView(withId(R.id.action_dishes)).check(
//            RecyclerViewItemCountAssertion(1)
//        )
//
//        onView(withId(R.id.add_action_dishes_button)).perform(click())
//        onView(withId(R.id.add_action_dishes_button)).perform(click())
//
//        onView(withId(R.id.action_dishes)).check(
//            RecyclerViewItemCountAssertion(3)
//        )
//
//        onView(withId(R.id.add_action_dishes_button)).perform(click())
//
//        onView(withId(R.id.action_dishes)).check(
//            RecyclerViewItemCountAssertion(4)
//        )
//
//    }
            adapter.notifyDataSetChanged()
        }

        val watcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) { }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val startTime = binding.timeToStartEdit.text.toString().trim()
                val duration = binding.durationEdit.text.toString().trim().toIntOrNull()

                binding.readyAddDishButton.isEnabled = startTime.isNotEmpty() && duration != null
            }
        }

        binding.timeToStartEdit.addTextChangedListener(watcher)
        binding.durationEdit.addTextChangedListener(watcher)

        binding.readyAddDishButton.setOnClickListener {
            val dishes = adapter.dishes.map {
                it.dishName.text.toString().trim() to it.dishCount.text.toString().trim()
            }.toMap()

            val duration = binding.durationEdit.text.toString().trim().toInt()

            viewModel.addAction(duration, dishes)

            findNavController().navigate(R.id.homeNavigationFragment)

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
                            .setText(dateFormat.format(calendar.time))

                        viewModel.setTime(calendar)

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
