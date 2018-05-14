package com.example.make_eat_easy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.R
import com.example.make_eat_easy.firebase.Authenticator
import com.example.make_eat_easy.viewmodels.ProductViewModel
import com.google.firebase.firestore.FirebaseFirestore


class ProductsAdapter(
    val productViewModel: ProductViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val CATEGORY = 1
        val PRODUCT = 2
    }

    private lateinit var productClickListener: (position: Int) -> Unit

    class CategoryHolder(categoryView: View) : RecyclerView.ViewHolder(categoryView) {
//        init {
//            categoryView.setFocusable(false);
//            categoryView.setEnabled(false);
//        }
        val categoryName = categoryView.findViewById<TextView>(R.id.category_text)
    }

    class ProductHolder(productView: View, productClickListener: (position: Int)  -> Unit) : RecyclerView.ViewHolder(productView) {
        val productNameView = productView.findViewById<TextView>(R.id.product_name)
        val measureNameView = productView.findViewById<TextView>(R.id.measure_name)
        init {
            productView.setOnClickListener { productClickListener(adapterPosition) }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = productViewModel.productsCategoryList.value!![position]

        if (getItemViewType(position) == PRODUCT) {
            val measureId = item.measureId
            val measure = productViewModel.productRepository.measures.value!!.find { it.measureId == measureId }
            (holder as ProductHolder).productNameView.text = item.productName

//            TODO:
            holder.measureNameView.text = measure?.measureName ?: ""
        } else {
            (holder as CategoryHolder).categoryName.text = item.categoryName
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (productViewModel.productsCategoryList.value!![position].isProduct) PRODUCT else CATEGORY


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == PRODUCT) {
            val productView = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)
            ProductsAdapter.ProductHolder(productView, productClickListener)
        } else {
            val categoryView = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false)
            ProductsAdapter.CategoryHolder(categoryView)
        }
    }

    override fun getItemCount() = productViewModel.productsCategoryList.value!!.size

    fun deleteProduct(position: Int) {

        val productId = productViewModel.productsCategoryList.value!![position].productId

        FirebaseFirestore
            .getInstance()
            .collection("userData")
            .document(Authenticator().getEmail())
            .collection("product")
            .document(productId.toString())
            .delete()

    }

    fun setProductClickListener(listener: (position: Int) -> Unit ) {
        productClickListener = listener
    }


}