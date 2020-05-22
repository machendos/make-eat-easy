package com.example.make_eat_easy.models

class Measure(
    var measureId: Int = 0,
    var measureName: String = "",
    var parentMeasureId: Int? = 0,
    var parentMeasureFactor: Double = 0.0
)
