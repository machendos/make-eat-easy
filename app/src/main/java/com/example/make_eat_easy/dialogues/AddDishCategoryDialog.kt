package com.example.make_eat_easy.dialogues

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.make_eat_easy.R
import com.example.make_eat_easy.databinding.AddCategoryDialogBinding
import com.example.make_eat_easy.viewmodels.DishesViewModel

class AddDishCategoryDialog(
    private val context: Context, val viewModel: DishesViewModel
) {

    fun show() {
        val bindingDialog: AddCategoryDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.add_category_dialog, null, false
        )

        val dialog = AlertDialog.Builder(context)
            .setView(bindingDialog.root)
            .setTitle("Add new category")
            .setPositiveButton("Add") { _, _ ->

                viewModel.addCategory(bindingDialog.addCategoryName.text.toString())

            }.setNegativeButton("Cancel") { _, _ -> }

        dialog.show()

    }

}