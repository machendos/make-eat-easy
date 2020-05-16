package com.example.make_eat_easy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.R

class NecessaryProductsAdapter :
    RecyclerView.Adapter<NecessaryProductsAdapter.ProductBlankHolder>() {

    var itemsCount = 1

    class ProductBlankHolder(blankView: View) : RecyclerView.ViewHolder(blankView) {
        val productName = blankView.findViewById<EditText>(R.id.necessary_product_name)
        val productCount = blankView.findViewById<EditText>(R.id.necessary_product_count)
        val position = blankView.findViewById<TextView>(R.id.position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductBlankHolder {
        return ProductBlankHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.necessary_product_element, parent, false)
        )
    }



    override fun onBindViewHolder(
        holder: NecessaryProductsAdapter.ProductBlankHolder,
        position: Int
    ) {

        holder.position.text = position.toString()
        }


    override fun getItemCount() = itemsCount

    override fun getItemViewType(position: Int): Int {
        return position
    }


}