package com.example.make_eat_easy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.R
import com.example.make_eat_easy.firebase.Authenticator
import com.example.make_eat_easy.models.Measure
import com.example.make_eat_easy.models.Product
import com.google.firebase.firestore.FirebaseFirestore


class NewAdapter(
    val products: MutableLiveData<MutableList<Product>>,
    val measures: MutableLiveData<MutableList<Measure>>
):
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

        val product = products.value!![position]
        val measureId = product.measureId
        val measure = measures.value!!.find { it.measureId == measureId }

        holder.productNameView.text = products.value!![position].productName
        holder.categoryNameView.text = products.value!![position].categoryId.toString()
        holder.measureNameView.text = measure!!.measureName
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

        FirebaseFirestore
            .getInstance()
            .collection("userData")
            .document(Authenticator().getEmail())
            .collection("product")
            .document(productId.toString())
            .delete()

    }


}