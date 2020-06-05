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
import com.example.make_eat_easy.views.AddAction
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.Is.`is`
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class AddActionViewTest {
    @Test
    fun radioGroup() {
        val scenario = launchFragmentInContainer<AddAction>()

        onView(withId(R.id.cooking_radio)).check(matches(isChecked()))
        onView(withId(R.id.eating_radio)).check(matches(isNotChecked()))
        onView(withId(R.id.other_radio)).check(matches(isNotChecked()))

    }

    @Test
    fun confirmButton() {
        val scenario = launchFragmentInContainer<AddAction>()

        onView(withId(R.id.ready_add_dish_button)).check(matches(allOf(
            withEffectiveVisibility(Visibility.VISIBLE),
            isFocusable(),
            isClickable(),
            isDisplayed(),
            withText("Add")
        )))
    }

    @Test
    fun radioChange() {
        val scenario = launchFragmentInContainer<AddAction>()

        onView(withId(R.id.eating_radio)).perform(click())

        onView(withId(R.id.cooking_radio)).check(matches(isNotChecked()))
        onView(withId(R.id.eating_radio)).check(matches(isChecked()))
        onView(withId(R.id.other_radio)).check(matches(isNotChecked()))

        onView(withId(R.id.other_radio)).perform(click())

        onView(withId(R.id.cooking_radio)).check(matches(isNotChecked()))
        onView(withId(R.id.eating_radio)).check(matches(isNotChecked()))
        onView(withId(R.id.other_radio)).check(matches(isChecked()))

        onView(withId(R.id.cooking_radio)).perform(click())

        onView(withId(R.id.cooking_radio)).check(matches(isChecked()))
        onView(withId(R.id.eating_radio)).check(matches(isNotChecked()))
        onView(withId(R.id.other_radio)).check(matches(isNotChecked()))
    }

    @Test
    fun necessaryDishesRecyclerCount() {
        val scenario = launchFragmentInContainer<AddAction>()

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

        onView(withId(R.id.action_dishes)).check(
            RecyclerViewItemCountAssertion(1)
        )

        onView(withId(R.id.add_action_dishes_button)).perform(click())
        onView(withId(R.id.add_action_dishes_button)).perform(click())

        onView(withId(R.id.action_dishes)).check(
            RecyclerViewItemCountAssertion(3)
        )

        onView(withId(R.id.add_action_dishes_button)).perform(click())

        onView(withId(R.id.action_dishes)).check(
            RecyclerViewItemCountAssertion(4)
        )

    }




}
