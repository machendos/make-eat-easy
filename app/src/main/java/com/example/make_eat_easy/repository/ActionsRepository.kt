package com.example.make_eat_easy.repository

import androidx.lifecycle.MutableLiveData
import com.example.make_eat_easy.models.Action
import java.util.*

object ActionsRepository {

    val actionsCollection = DB.actionCollection

    val actions: MutableLiveData<MutableList<Action>> = MutableLiveData(mutableListOf())


    init {
        actionsCollection.addSnapshotListener { snapshot, _ ->
            actions.value =
                snapshot!!.documents.map {

                val startYear = it.get("startYear") as Long
                val startMonth = it.get("startMonth") as Long
                val srartDat = it.get("srartDat") as Long
                val startHour = it.get("startHour") as Long
                val startMinute = it.get("startMinute") as Long
                val duration = it.get("duration") as Long
                val type = it.get("type") as Long
                val dishes = it.get("dishes") as MutableMap<String, Double>
                val actionId = it.get("actionId") as Long

                Action(
                    startYear.toInt(),
                startMonth.toInt(),
                srartDat.toInt(),
                startHour.toInt(),
                startMinute.toInt(),
                duration.toInt(),
                type.toInt(),
                dishes,
                actionId.toInt()
                )

            } as MutableList<Action>

        }

    }

    fun addAction(type: Int, start: Calendar, duration: Int, dishes: Map<String, Double>) {

        actions.value!!.sortByDescending { it.actionId }
        val id = if (actions.value!!.size == 0) 0 else actions.value!![0].actionId + 1

        actionsCollection.document(id.toString()).set(Action(
            start.get(Calendar.YEAR),
            start.get(Calendar.MONTH),
            start.get(Calendar.DAY_OF_MONTH),
            start.get(Calendar.HOUR_OF_DAY),
            start.get(Calendar.MINUTE),
            duration,
            type,
            dishes.toMutableMap(),
            id
        ))
    }

}