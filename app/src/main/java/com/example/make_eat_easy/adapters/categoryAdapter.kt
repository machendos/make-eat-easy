package com.example.make_eat_easy.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.R
import com.example.make_eat_easy.models.Category
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class CategoryAdapter(options: FirestoreRecyclerOptions<Category>):
    FirestoreRecyclerAdapter<Category, CategoryAdapter.CategoryHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return CategoryHolder(v)
    }


    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {

        Log.d("asdasd", "$fromPosition $toPosition")


        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {

                val from = snapshots.getSnapshot(i).get("order")
                val to = snapshots.getSnapshot(i + 1).get("order")

                snapshots.getSnapshot(i).reference.update("order", to)
                snapshots.getSnapshot(i + 1).reference.update("order", from)

                Log.d("asdasd", "$from $to")

            }
        } else {
            for (i in fromPosition downTo  toPosition + 1) {
                val from = snapshots.getSnapshot(i).get("order")
                val to = snapshots.getSnapshot(i - 1).get("order")

                snapshots.getSnapshot(i).reference.update("order", to)
                snapshots.getSnapshot(i - 1).reference.update("order", from)
                Log.d("asdasd", "$from $to")
            }
        }
        return true
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)


    }

    fun deleteItem(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }


    override fun onBindViewHolder(holder: CategoryHolder, position: Int, model: Category) {
//        holder.categoryId.text = model.categoryName
//        holder.categoryName.text = model.order.toString()
    }

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val categoryId: TextView = itemView.findViewById(R.id)
//        val categoryName: TextView = itemView.findViewById(R.id.category_text)
    }

}