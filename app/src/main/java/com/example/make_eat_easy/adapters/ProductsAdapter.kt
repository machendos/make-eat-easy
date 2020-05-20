package com.example.make_eat_easy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.R
import com.example.make_eat_easy.viewmodels.ProductViewModel

class ProductsAdapter(
    productViewModel: ProductViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val productsCategoryList = productViewModel.productsCategoryList
    private val measures = productViewModel.productRepository.measures

    companion object {
        const val CATEGORY = 1
        const val PRODUCT = 2
    }

    private lateinit var productClickListener: (position: Int) -> Unit

    fun setProductClickListener(listener: (position: Int) -> Unit ) {
        productClickListener = listener
    }

    class CategoryHolder(categoryView: View) :
        RecyclerView.ViewHolder(categoryView) {
        val categoryName: TextView =
            categoryView.findViewById(R.id.category_text)
    }

    class ProductHolder(
        productView: View, productClickListener: (position: Int)  -> Unit
    ) : RecyclerView.ViewHolder(productView) {
        val productNameView: TextView =
            productView.findViewById(R.id.product_name)
        val measureNameView: TextView =
            productView.findViewById(R.id.measure_name)
        init {
            productView.setOnClickListener { productClickListener(adapterPosition) }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = productsCategoryList.value!![position]

        if (getItemViewType(position) == PRODUCT) {
            val measureId = item.measureId
            val measure = measures.value!!.find { it.measureId == measureId }
            (holder as ProductHolder).productNameView.text = item.productName

//            TODO:
            holder.measureNameView.text = measure?.measureName ?: ""
        } else {
            (holder as CategoryHolder).categoryName.text = item.categoryName
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (productsCategoryList.value!![position].isProduct) PRODUCT else CATEGORY


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == PRODUCT) {
            val productView = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)
            ProductHolder(productView, productClickListener)
        } else {
            val categoryView = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false)
            CategoryHolder(categoryView)
        }
    }

    override fun getItemCount() = productsCategoryList.value!!.size

}