package com.example.make_eat_easy.models

class Action (
    var startYear: Int = 2020,
    var startMonth: Int = 1,
    var srartDat: Int = 1,
    var startHour: Int = 0,
    var startMinute: Int = 0,
    var duration: Int = 0,
    var type: Int = 0,
    var dishes: MutableMap<String, Double> = mutableMapOf(),
    var actionId: Int = 0
)