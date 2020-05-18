package com.example.make_eat_easy.util

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.adapters.DishesAdapter
import com.example.make_eat_easy.viewmodels.DishesViewModel
import com.google.android.material.snackbar.Snackbar

class DishTouchHelper(

    val viewModel: DishesViewModel,
    private val dishAdapter: DishesAdapter, val view: View
) : ItemTouchHelper.SimpleCallback(0, 0) {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return if (viewHolder.itemViewType == DishesAdapter.CATEGORY) 0
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

        val list = viewModel.dishesCategoryList.value!!

        val draggedElement = list[draggedPosition]
        val targetElement = list[targetPosition]

        val draggedOrder = draggedElement.order
        draggedElement.order = targetElement.order
        targetElement.order = draggedOrder

        list[draggedPosition] = targetElement
        list[targetPosition] = draggedElement

        dishAdapter.notifyItemMoved(draggedPosition, targetPosition)
        return false
    }

    override fun onSelectedChanged(
        viewHolder: RecyclerView.ViewHolder?, actionState: Int
    ) {

        Log.d("asdasd-", viewModel.dishesCategoryList.value!!.toString())

        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            var currCategoryId: Int? = null
            viewModel.dishesCategoryList.value!!.forEach {
                if (it.isDish) it.categoryId = currCategoryId
                else currCategoryId = it.categoryId
            }

            viewModel
                .dishesRepository
                .updateDishOrders(viewModel.dishesCategoryList.value!!)
        }
        super.onSelectedChanged(viewHolder, actionState)
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        val id = viewModel
            .dishesCategoryList
            .value!![viewHolder.adapterPosition]
            .dishId

        viewModel.deleteDish(id)

        val productDeletedSnackbar = Snackbar.make(view, "Product removed", Snackbar.LENGTH_LONG)
        productDeletedSnackbar.setAction("UNDO") {
            viewModel.unremoveDish()
            Toast.makeText(view.context, "Undo", Toast.LENGTH_SHORT).show()
        }
        productDeletedSnackbar.show()

    }
}
