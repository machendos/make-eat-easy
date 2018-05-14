package com.example.make_eat_easy.dialogues

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.make_eat_easy.R
import com.example.make_eat_easy.databinding.ProductEditDialogBinding
import com.example.make_eat_easy.viewmodels.ProductViewModel
import com.google.android.material.snackbar.Snackbar

class EditProductDialog(
    private val context: Context, val viewModel: ProductViewModel, val view: View
) {

    fun show(position: Int) {

        val bindingDialog: ProductEditDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.product_edit_dialog,
            null,
            false
        )

        val oldProduct = viewModel.productsCategoryList.value!![position]
        bindingDialog.product = viewModel.productsCategoryList.value!![position]

        val dialog = AlertDialog.Builder(context)
            .setView(bindingDialog.root)
            .setPositiveButton("Ok") { _, _ ->
                val newProductName = bindingDialog.productName.text.toString()
                viewModel.updateProduct(oldProduct.productId, newProductName)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()

        bindingDialog.productDeleteButton.setOnClickListener {
            viewModel.deleteProduct(oldProduct.productId)
            val productDeletedSnackbar = Snackbar.make(view, "Product removed", Snackbar.LENGTH_LONG)
            productDeletedSnackbar.setAction("UNDO") {
                viewModel.unremoveProduct()
                Toast.makeText(context, "Undo", Toast.LENGTH_SHORT).show()
            }
            productDeletedSnackbar.show()
            dialog.dismiss()

        }

    }
}