package com.example.make_eat_easy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.R
import com.example.make_eat_easy.viewmodels.DishesViewModel

class DishesAdapter(val dishesViewModel: DishesViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val CATEGORY = 1
        val DISH = 2
    }

    private lateinit var dishClickListener: (position: Int) -> Unit

    class CategoryHolder(categoryView: View) : RecyclerView.ViewHolder(categoryView) {
        //        init {
//            categoryView.setFocusable(false);
//            categoryView.setEnabled(false);
//        }
        val categoryName = categoryView.findViewById<TextView>(R.id.category_text)
    }

    class DishHolder(productView: View, dishClickListener: (position: Int)  -> Unit) : RecyclerView.ViewHolder(productView) {
        val dishNameView = productView.findViewById<TextView>(R.id.dish_name)
        init {
            productView.setOnClickListener { dishClickListener(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DISH) {
            val dishView = LayoutInflater.from(parent.context)
                .inflate(R.layout.dish_item, parent, false)
            DishHolder(dishView, dishClickListener)
        } else {
            val categoryView = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false)
            CategoryHolder(categoryView)
        }
    }

    override fun getItemCount() = dishesViewModel.dishesCategoryList.value!!.size

    override fun getItemViewType(position: Int) =
        if (dishesViewModel.dishesCategoryList.value!![position].isDish) DISH else CATEGORY

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = dishesViewModel.dishesCategoryList.value!![position]
        if (getItemViewType(position) == DISH) {
            val dishName = item.dishName
            (holder as DishHolder).dishNameView.text = item.dishName
        } else {
            (holder as CategoryHolder).categoryName.text = item.categoryName
        }

    }

    fun setDishClickListener(listener: (position: Int) -> Unit ) {
        dishClickListener = listener
    }

}