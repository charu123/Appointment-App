package com.recipeapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val prepTimeMinutes: Int,
    val cookTimeMinutes: Int,
    val servings: Int,
    val difficulty: Difficulty,
    val category: Category,
    val imageUrl: String? = null,
    val isFavorite: Boolean = false
)

@Serializable
enum class Difficulty {
    EASY, MEDIUM, HARD
}

@Serializable
enum class Category {
    APPETIZER,
    MAIN_COURSE,
    DESSERT,
    SIDE_DISH,
    SOUP,
    SALAD,
    BEVERAGE,
    BREAKFAST,
    SNACK
}