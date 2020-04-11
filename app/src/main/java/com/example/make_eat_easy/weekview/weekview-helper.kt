package com.example.make_eat_easy.weekview

import android.app.Activity
import androidx.core.content.ContextCompat
import com.alamkanak.weekview.WeekView
import com.example.make_eat_easy.R
import java.util.*

fun setT(context: Activity) {
    val color1 = ContextCompat.getColor(context, R.color.colorAccent)
    val event = Event(123, "title",


        Calendar.getInstance().apply {
            set(Calendar.YEAR, 2020)
            set(Calendar.MONTH, 3)
            set(Calendar.DAY_OF_MONTH, 8)
            set(Calendar.HOUR_OF_DAY, 11)
            set(Calendar.MINUTE, 11)
            set(Calendar.SECOND, 12)
            set(Calendar.MILLISECOND, 12)
        },

        Calendar.getInstance().apply {
            set(Calendar.YEAR, 2020)
            set(Calendar.MONTH, 3)
            set(Calendar.DAY_OF_MONTH, 8)
            set(Calendar.HOUR_OF_DAY, 12)
            set(Calendar.MINUTE, 11)
            set(Calendar.SECOND, 12)
            set(Calendar.MILLISECOND, 12)
        },

        "123", color1, false, true)

    context.findViewById<WeekView<Event>>(R.id.weekView).submit(
        listOf(event.toWeekViewEvent())
    )
}