package com.sofaacademy.sofaminiproject.model

enum class GoalType(val goalType: String) {
    REGULAR("regular"),
    OWN_GOAL("owngoal"),
    PENALTY("penalty"),
    ONE_POINT("onepoint"),
    TWO_POINT("twopoint"),
    THREE_POINT("threepoint"),
    TOUCHDOWN("touchdown"),
    SAFETY("safety"),
    FIELD_GOAL("fieldgoal"),
    EXTRA_POINT("extrapoint")
}
