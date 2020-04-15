package com.example.make_eat_easy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.R
import com.example.make_eat_easy.models.Category
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class CategoryAdapter(options: FirestoreRecyclerOptions<Category>):
    FirestoreRecyclerAdapter<Category, CategoryAdapter.CategoryHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CategoryHolder(v)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int, model: Category) {
        holder.categoryId.text = model.categoryId
        holder.categoryName.text = model.categoryName
    }

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryId: TextView = itemView.findViewById(R.id.id_text)
        val categoryName: TextView = itemView.findViewById(R.id.category_text)
    }

}