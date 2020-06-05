package com.example.make_eat_easy

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.make_eat_easy.views.AddDish
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.Is.`is`
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class AddDishViewTest {

    @Test
    fun confirmButton() {
        val scenario = launchFragmentInContainer<AddDish>()

        onView(withId(R.id.ready_add_dish_button)).check(matches(allOf(
            withEffectiveVisibility(Visibility.VISIBLE),
            isFocusable(),
            isClickable(),
            isEnabled(),
            withText("Add")
        )))
    }

    @Test
    fun necessaryProductRecyclerCount() {
        val scenario = launchFragmentInContainer<AddDish>()

        class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
            override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
                if (noViewFoundException != null) {
                    throw noViewFoundException
                }
                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter
                assertThat(adapter!!.itemCount, `is`(expectedCount))
            }

        }

        onView(withId(R.id.necessary_products)).check(
            RecyclerViewItemCountAssertion(1)
        )

        onView(withId(R.id.add_necessary_product_button)).perform(click())
        onView(withId(R.id.add_necessary_product_button)).perform(click())

        onView(withId(R.id.necessary_products)).check(
            RecyclerViewItemCountAssertion(3)
        )

        onView(withId(R.id.add_necessary_product_button)).perform(click())

        onView(withId(R.id.necessary_products)).check(
            RecyclerViewItemCountAssertion(4)
        )

    }

    @Test
    fun confirmFragment() {
        val scenario = launchFragmentInContainer<AddDish>()
        onView(withId(R.id.category_name))
            .check(matches(isEnabled()))
    }

}
