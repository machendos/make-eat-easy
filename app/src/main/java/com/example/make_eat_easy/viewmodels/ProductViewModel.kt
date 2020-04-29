package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.firebase.ProductsRepository

class ProductViewModel: ViewModel() {

    val productRepository = ProductsRepository()

}