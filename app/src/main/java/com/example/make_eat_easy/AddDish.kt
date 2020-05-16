package com.example.make_eat_easy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.adapters.NecessaryProductsAdapter
import com.example.make_eat_easy.databinding.AddDishBinding
import com.example.make_eat_easy.viewmodels.DishesViewModel


class AddDish : Fragment() {

    private lateinit var viewModel: DishesViewModel
    private  lateinit var binding: AddDishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<AddDishBinding>(inflater, R.layout.add_dish, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(DishesViewModel::class.java)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = NecessaryProductsAdapter()

        val recyclerView = activity?.findViewById<RecyclerView>(R.id.necessary_products)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
    }

}
