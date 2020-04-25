package com.example.make_eat_easy.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.R
import com.example.make_eat_easy.firebase.Authenticator
import com.example.make_eat_easy.models.Product
import com.google.firebase.firestore.FirebaseFirestore


class NewAdapter(val products: MutableLiveData<MutableList<Product>>):
    RecyclerView.Adapter<NewAdapter.ProductHolder>() {

//    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//    }

    class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameView = itemView.findViewById<TextView>(R.id.product_name)
        val categoryNameView = itemView.findViewById<TextView>(R.id.category_name)
        val measureNameView = itemView.findViewById<TextView>(R.id.measure_name)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.productNameView.text = products.value!![position].productName
        holder.categoryNameView.text = products.value!![position].categoryId
        holder.measureNameView.text = products.value!![position].measureId
    }

//    override fun getItemViewType(position: Int): Int {
//        return if (TextUtils.isEmpty(employees.get(position).getEmail())) {
////            TYPE_CALL
//        } else {
////            TYPE_EMAIL
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NewAdapter.ProductHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ProductHolder(textView)
    }

    override fun getItemCount() = products.value!!.size

    fun deleteProduct(position: Int) {
        val productId = products.value!![position].productId
        products.value!!.removeAt(position)
        notifyDataSetChanged()

        FirebaseFirestore
            .getInstance()
            .collection("userData")
            .document(Authenticator().getEmail())
            .collection("product").document(productId + "asd").delete().addOnFailureListener {
                Log.d("debuggg", it.message)
            }.addOnSuccessListener {
                Log.d("debuggg", "REAT")
            }

    }


}