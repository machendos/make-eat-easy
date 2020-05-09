package com.example.make_eat_easy.views

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.app.DialogCompat
import androidx.fragment.app.DialogFragment
import com.example.make_eat_easy.R

class AddProductDialog: AppCompatDialogFragment() {



    override fun onCreate(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        val builder = AlertDialog.Builder(activity)

        val inflater = activity?.layoutInflater;
        val view = inflater?.inflate(R.layout.add_product_dialog, null)

        val productName = view?.findViewById<EditText>(R.id.product_name)
        builder.setView(view)
            .setPositiveButton("Ok") {

            }
            .setNegativeButton("not ok") {

            }

        return builder.create()

    }
}