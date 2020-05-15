package com.example.make_eat_easy.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.make_eat_easy.R
import com.example.make_eat_easy.databinding.DishesFragmentBinding
import com.example.make_eat_easy.firebase.DishesRepository
import com.example.make_eat_easy.viewmodels.DishesViewModel


class Dishes : Fragment() {

    companion object {
        fun newInstance() = Dishes()
    }

    private lateinit var viewModel: DishesViewModel
    private  lateinit var binding: DishesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        TODO: to start activity
        DishesRepository()

        binding = DataBindingUtil.inflate<DishesFragmentBinding>(inflater, R.layout.dishes_fragment, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(DishesViewModel::class.java)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.dishesCategoryList.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }


        // TODO: Use the ViewModel
    }

}
