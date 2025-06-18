package com.projects.android.recipebook.model.enums

enum class Course(value: String) {
    STARTER("Стартер"), FIRST("Первое"), SECOND("Второе"), SIDE("Закуска"), DESSERT("Дессерт");

    private val valueString: String = value

    override fun toString(): String {
        return valueString
    }
}