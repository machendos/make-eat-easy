package com.example.make_eat_easy.dialogues

import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.make_eat_easy.R
import com.example.make_eat_easy.databinding.AddProductDialogBinding
import com.example.make_eat_easy.viewmodels.ProductViewModel

class AddProductDialog(private val context: Context, val viewModel: ProductViewModel) {

    fun show() {

        val bindingDialog: AddProductDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.add_product_dialog, null, false
        )

        val dialog = AlertDialog.Builder(context)
            .setView(bindingDialog.root)
            .setTitle("Add new product")
            .setPositiveButton("Add") { _, _ ->

                viewModel.addProduct(
                    bindingDialog.productName.text.toString(),
                    bindingDialog.measureName.text.toString(),
                    bindingDialog.categoryName.text.toString()
                )

            }.setNegativeButton("Cancel") { _, _ -> }
        dialog.show()

        bindingDialog.categoryName.setAdapter(ArrayAdapter<String>(
            context,
            R.layout.element_autocomplete,
            R.id.autocomplete_element,
            viewModel.productRepository.categories.value!!.map { it.categoryName }
        ))

        bindingDialog.measureName.setAdapter(ArrayAdapter<String>(
            context,
            R.layout.element_autocomplete,
            R.id.autocomplete_element,
            viewModel.productRepository.measures.value!!.map { it.measureName }
        ))
    }
}