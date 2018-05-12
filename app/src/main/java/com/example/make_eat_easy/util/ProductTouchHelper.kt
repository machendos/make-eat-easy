package com.example.make_eat_easy.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.adapters.ProductsAdapter
import com.example.make_eat_easy.viewmodels.ProductViewModel

class ProductTouchHelper(
    val viewModel: ProductViewModel, private val productAdapter: ProductsAdapter
) : ItemTouchHelper.SimpleCallback(0, 0) {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return if (viewHolder.itemViewType == ProductsAdapter.CATEGORY) 0
        else ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun isLongPressDragEnabled() = true

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val targetPosition = target.adapterPosition
        if (targetPosition == 0) return false
        val draggedPosition = viewHolder.adapterPosition

        val list = viewModel.productsCategoryList.value!!

        val draggedElement = list[draggedPosition]
        val targetElement = list[targetPosition]

        val draggedOrder = draggedElement.order
        draggedElement.order = targetElement.order
        targetElement.order = draggedOrder

        list[draggedPosition] = targetElement
        list[targetPosition] = draggedElement

        productAdapter.notifyItemMoved(draggedPosition, targetPosition)
        return false
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            var currCategoryId: Int? = null
            viewModel.productsCategoryList.value!!.forEach {
                if (it.isProduct) it.categoryId = currCategoryId
                else currCategoryId = it.categoryId
            }

            viewModel.productRepository.updateProductOrders(viewModel.productsCategoryList.value!!)
        }
        super.onSelectedChanged(viewHolder, actionState)
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        val id = viewModel
            .productsCategoryList
            .value!![viewHolder.adapterPosition]
            .productId

        viewModel.productRepository.deleteProduct(id)

    }
}
