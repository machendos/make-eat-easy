package com.example.make_eat_easy.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.make_eat_easy.R
import com.example.make_eat_easy.adapters.DishesAdapter
import com.example.make_eat_easy.databinding.DishesFragmentBinding
import com.example.make_eat_easy.viewmodels.DishesViewModel


class Dishes : Fragment() {

    companion object {
        fun newInstance() = Dishes()
    }

    private val viewModel: DishesViewModel by activityViewModels()
    private  lateinit var binding: DishesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<DishesFragmentBinding>(inflater, R.layout.dishes_fragment, container, false)
        val view = binding.root

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dishesAdapter = DishesAdapter(viewModel)

        viewModel.dishesCategoryList.observe(viewLifecycleOwner) {
            dishesAdapter.notifyDataSetChanged()
        }

        dishesAdapter.setDishClickListener {
            Log.d("asdasd->click", it.toString())
        }

        val recyclerView = binding.dishesView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = dishesAdapter

        val navController = findNavController()

        binding.goToAddDish.setOnClickListener { navController.navigate(R.id.addDish) }


        // TODO: Use the ViewModel
    }

}
