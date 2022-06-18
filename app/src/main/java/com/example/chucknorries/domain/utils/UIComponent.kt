package com.example.chucknorries.domain.utils

sealed class UIComponent {
    data class Dialog(
        val title:String,
        val description:String
    ):UIComponent()

    data class None(
        val description: String
    ):UIComponent()
}