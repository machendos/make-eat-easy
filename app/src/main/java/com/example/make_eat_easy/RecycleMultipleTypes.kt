package com.example.make_eat_easy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.adapters.NewAdapter
import com.example.make_eat_easy.firebase.ProductsRepository


class RecycleMultipleTypes : AppCompatActivity() {

    lateinit var adapter: NewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_interact)

        val productRepository = ProductsRepository()
        productRepository.products.observe(this) {
            Log.d("listSize", it.size.toString())
        }

        adapter = NewAdapter(productRepository.products)

        showCategories()

    }


    fun showCategories() {

        val rc = findViewById<RecyclerView>(R.id.recycler_v)
        rc.setHasFixedSize(true)
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = adapter

//        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.LEFT) {
//
//            override fun getMovementFlags(
//                recyclerView: RecyclerView,
//                viewHolder: ViewHolder
//            ): Int {
//                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
//                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
//                return ItemTouchHelper.Callback.makeMovementFlags(
//                    dragFlags,
//                    swipeFlags
//                )
//            }
////            override fun isLongPressDragEnabled(): Boolean {
////                return true
////            }
//            override fun isItemViewSwipeEnabled(): Boolean {
//                return true
//            }
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: ViewHolder,
//                target: ViewHolder
//            ): Boolean {
//
//                Toast.makeText(applicationContext, "Move", Toast.LENGTH_LONG).show()
//                    adapter.onItemMove(viewHolder.adapterPosition,
//                        target.adapterPosition
//                    )
//                return true
//            }
//            override fun onMoved(
//                recyclerView: RecyclerView,
//                viewHolder: ViewHolder,
//                fromPos: Int,
//                target: ViewHolder,
//                toPos: Int,
//                x: Int,
//                y: Int
//            ) {
//                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
//            }
//            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
//
//                adapter.deleteItem(viewHolder.adapterPosition)
//            }
//        }).attachToRecyclerView(rc)


    }

//    override fun onStart() {
//        super.onStart()
//        adapter.startListening()
//    }

//    override fun onStop() {
//        super.onStop()
//        adapter.stopListening()
//    }

}


