package com.universall.watertracker.main.features.settings.domain.entities


object SettingsDefaults {
    const val DAILY_GOAL_ML = 1500
    const val REMINDERS_ENABLED = false
    const val ADD_BUTTON_VALUE = 300
}


data class SettingsState(
    val dailyGoalMl: Int,
    val remindersEnabled: Boolean,
    val addButtonValue: Int
) {
    constructor(
        dailyGoalMl: Int? = null,
        remindersEnabled: Boolean? = null,
        addButtonValue: Int? = null
    ) : this(
        dailyGoalMl = dailyGoalMl ?: SettingsDefaults.DAILY_GOAL_ML,
        remindersEnabled = remindersEnabled ?: SettingsDefaults.REMINDERS_ENABLED,
        addButtonValue = addButtonValue ?: SettingsDefaults.ADD_BUTTON_VALUE
    )
}
