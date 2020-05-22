package com.example.make_eat_easy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.R
import com.example.make_eat_easy.viewmodels.AddActionViewModel

class DishesForActionAdapter(val context: Context, val viewModel: AddActionViewModel) :
    RecyclerView.Adapter<DishesForActionAdapter.DishBlankHolder>() {

    val dishes = mutableListOf<DishBlankHolder>()

    var itemsCount = 1

    class DishBlankHolder(blankView: View, viewModel: AddActionViewModel, context: Context) :
        RecyclerView.ViewHolder(blankView) {
        val dishName = blankView.findViewById<AutoCompleteTextView>(R.id.dish_name_edit)
        val dishCount = blankView.findViewById<EditText>(R.id.serv_number_edit)
        val position = blankView.findViewById<TextView>(R.id.position)

        init {
            dishName.setAdapter(
                ArrayAdapter<String>(context,
                    R.layout.element_autocomplete,
                    R.id.autocomplete_element,
                    viewModel.dishesList.value!!.map { it.dishName })
            )
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DishesForActionAdapter.DishBlankHolder {

        val view = DishBlankHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.add_dish_to_action_element, parent, false),
            viewModel, context
        )
        dishes.add(view)

        return view

    }


    override fun onBindViewHolder(
        holder: DishBlankHolder,
        position: Int
    ) {

        holder.position.text = position.toString()
    }


    override fun getItemCount() = itemsCount

    override fun getItemViewType(position: Int): Int {
        return position
    }


}