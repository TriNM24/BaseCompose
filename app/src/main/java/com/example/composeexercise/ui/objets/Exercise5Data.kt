package com.example.composeexercise.ui.objets

data class Exercise5Data(
    val id: Int,
    val imageLink: String,
    val name: String,
    val content: String = "Making a grid layout using the experimental LazyVerticalGrid composable, Column vertical scroll, Change TopAppBar title dynamically in different screens (using a shared view model),Navigation back button on TopAppBar."
)
