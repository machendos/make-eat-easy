package com.example.make_eat_easy

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.adapters.NewAdapter
import com.example.make_eat_easy.firebase.ProductsRepository
import kotlin.random.Random


class RecycleMultipleTypes : AppCompatActivity() {

    lateinit var adapter: NewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_interact)


        val productRepository = ProductsRepository()

        adapter = NewAdapter(productRepository.products)

        productRepository.products.observe(this) {
            adapter.notifyDataSetChanged()
        }


        showCategories()

        findViewById<Button>(R.id.set_category_button).setOnClickListener {
            val product = findViewById<EditText>(R.id.product_name_edit).text.toString()
            val category = findViewById<EditText>(R.id.category_name_edit).text.toString()
            val measure = findViewById<EditText>(R.id.measure_name_edit).text.toString()
            productRepository.addProduct(Random.nextInt().toString(), product, category, measure)
        }

    }


    fun showCategories() {

        val rc = findViewById<RecyclerView>(R.id.recycler_v)
        rc.setHasFixedSize(true)
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = adapter

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.LEFT) {

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return ItemTouchHelper.Callback.makeMovementFlags(
                    dragFlags,
                    swipeFlags
                )
            }
//            override fun isLongPressDragEnabled(): Boolean {
//                return true
//            }
            override fun isItemViewSwipeEnabled(): Boolean {
                return true
            }
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

//                Toast.makeText(applicationContext, "Move", Toast.LENGTH_LONG).show()
//                    adapter.onItemMove(viewHolder.adapterPosition,
//                        target.adapterPosition
//                    )
                return false
            }
            override fun onMoved(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                fromPos: Int,
                target: RecyclerView.ViewHolder,
                toPos: Int,
                x: Int,
                y: Int
            ) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                adapter.deleteProduct(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(rc)


    }

}


