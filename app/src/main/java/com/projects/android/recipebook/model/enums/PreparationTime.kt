package com.projects.android.recipebook.model.enums

enum class PreparationTime(value: String) {
    FIVE_MIN("5 Минут"), THIRTY_MIN("30 Минут"), ONE_HOUR("1 Час"), TWO_HOURS("2 Часа"), FOUR_HOURS(
        "4 Часа"
    ),
    UNLIMITED("Без лимита");

    private val valueString: String = value

    override fun toString(): String {
        return valueString
    }
}
