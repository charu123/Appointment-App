package com.recipeapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.recipeapp.ui.screens.RecipeListScreen
import com.recipeapp.ui.screens.RecipeDetailScreen
import com.recipeapp.ui.screens.FavoritesScreen
import com.recipeapp.viewmodel.RecipeViewModel

@Composable
fun RecipeApp(
    viewModel: RecipeViewModel = remember { RecipeViewModel() }
) {
    var currentScreen by remember { mutableStateOf(AppScreen.RecipeList) }
    var selectedRecipeId by remember { mutableStateOf<String?>(null) }
    
    MaterialTheme(
        colors = lightColors(
            primary = Color(0xFF6200EA),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC6),
            background = Color(0xFFF5F5F5)
        )
    ) {
        when (currentScreen) {
            AppScreen.RecipeList -> {
                RecipeListScreen(
                    viewModel = viewModel,
                    onRecipeClick = { recipeId ->
                        selectedRecipeId = recipeId
                        currentScreen = AppScreen.RecipeDetail
                    },
                    onNavigateToFavorites = {
                        currentScreen = AppScreen.Favorites
                    }
                )
            }
            AppScreen.RecipeDetail -> {
                selectedRecipeId?.let { recipeId ->
                    RecipeDetailScreen(
                        recipeId = recipeId,
                        viewModel = viewModel,
                        onBackClick = {
                            currentScreen = AppScreen.RecipeList
                        }
                    )
                }
            }
            AppScreen.Favorites -> {
                FavoritesScreen(
                    viewModel = viewModel,
                    onRecipeClick = { recipeId ->
                        selectedRecipeId = recipeId
                        currentScreen = AppScreen.RecipeDetail
                    },
                    onBackClick = {
                        currentScreen = AppScreen.RecipeList
                    }
                )
            }
        }
    }
}

enum class AppScreen {
    RecipeList,
    RecipeDetail,
    Favorites
}