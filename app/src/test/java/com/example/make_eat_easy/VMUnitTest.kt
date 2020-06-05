package com.example.make_eat_easy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.make_eat_easy.models.Category
import com.example.make_eat_easy.models.Product
import com.example.make_eat_easy.repository.ProductRepositoryInterface
import com.example.make_eat_easy.viewmodels.ProductViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class VMUnitTest {

    lateinit var mockedProductRepository: ProductRepositoryInterface
    lateinit var viewModel: ProductViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {

        mockedProductRepository = Mockito
            .mock(ProductRepositoryInterface::class.java)

        Mockito.`when`(mockedProductRepository.products)
            .thenReturn(MutableLiveData<MutableList<Product>>())
        Mockito.`when`(mockedProductRepository.categories)
            .thenReturn(MutableLiveData<MutableList<Category>>())

        viewModel = ProductViewModel(
            mockedProductRepository
        )
    }

    @Test
    fun simpleAddProduct() {

        Assert.assertEquals(viewModel.productsCategoryList.value!!.size, 0)

        viewModel.addProductLocal(12)

        Assert.assertEquals(viewModel.productsCategoryList.value!!.size, 1)
        Assert.assertEquals(viewModel.productsCategoryList.value!![0].productName, "product")

        viewModel.addProductLocal(10, "product2")
        Assert.assertEquals(viewModel.productsCategoryList.value!!.size, 2)
        Assert.assertEquals(viewModel.productsCategoryList.value!![1].productId, 10)

    }

    @Test
    fun editProduct() {

        val ID = 2

        viewModel.addProductLocal(ID, "product1")

        viewModel.editProductLocal(ID, "new-name")
        val edited = viewModel.productsCategoryList.value!!.find { it.productId == ID }
        Assert.assertEquals(edited!!.productName, "new-name")

    }

    @Test
    fun deleteProduct() {

        val START_ID = 1
        viewModel.addProductLocal(START_ID, "product1")
        viewModel.addProductLocal(START_ID + 1, "product2")
        viewModel.addProductLocal(START_ID + 2, "product3")

        viewModel.deleteProductLocal(START_ID)
        Assert.assertEquals(viewModel.productsCategoryList.value!!.size, 2)
        Assert.assertEquals(viewModel.productsCategoryList.value!![0].productId, START_ID + 1)
        Assert.assertEquals(viewModel.productsCategoryList.value!![1].productId, START_ID + 2)

    }

    @Test
    fun incorrectDeleteProduct() {

        val START_ID = 1
        viewModel.addProductLocal(START_ID, "product1")
        viewModel.addProductLocal(START_ID + 1, "product2")
        viewModel.addProductLocal(START_ID + 2, "product3")

        viewModel.deleteProductLocal(START_ID + 10)
        Assert.assertEquals(viewModel.productsCategoryList.value!!.size, 3)
        Assert.assertEquals(viewModel.productsCategoryList.value!![0].productId, START_ID)
        Assert.assertEquals(viewModel.productsCategoryList.value!![1].productId, START_ID + 1)
        Assert.assertEquals(viewModel.productsCategoryList.value!![2].productId, START_ID + 2)

    }

    @Test
    fun deleteCategory() {

        viewModel.addCategoryLocal(1, "category1")
        viewModel.addCategoryLocal(2, "category2")

        viewModel.addProductLocal(1, "product1", 1)
        viewModel.addProductLocal(2, "product2", 1)
        viewModel.addProductLocal(3, "product3", 1)

        viewModel.addProductLocal(4, "product4", 2)
        viewModel.addProductLocal(5, "product5", 2)
        viewModel.addProductLocal(6, "product6", 2)

        viewModel.deleteCategoryLocal(1)

        val firstProduct = viewModel.productsCategoryList.value!!.find { it.productId == 1 }
        val secondProduct = viewModel.productsCategoryList.value!!.find { it.productId == 2 }
        val thirdProduct = viewModel.productsCategoryList.value!!.find { it.productId == 3 }
        val fourthProduct = viewModel.productsCategoryList.value!!.find { it.productId == 4 }

        Assert.assertEquals(viewModel.productsCategoryList.value!!.size, 7)

        Assert.assertEquals(firstProduct!!.categoryId, null)
        Assert.assertEquals(secondProduct!!.categoryId, null)
        Assert.assertEquals(thirdProduct!!.categoryId, null)
        Assert.assertEquals(fourthProduct!!.categoryId, 2)

    }
}
