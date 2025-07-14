package com.recipeapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.recipeapp.model.Recipe
import com.recipeapp.viewmodel.RecipeViewModel

@Composable
fun FavoritesScreen(
    viewModel: RecipeViewModel,
    onRecipeClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val favoriteRecipes by viewModel.favoriteRecipes.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { Text("Favorite Recipes") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = MaterialTheme.colors.primary
        )
        
        if (favoriteRecipes.isEmpty()) {
            // Empty State
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "No favorite recipes yet",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Add some recipes to your favorites to see them here",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            }
        } else {
            // Favorites List
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoriteRecipes) { recipe ->
                    FavoriteRecipeCard(
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe.id) },
                        onRemoveFavorite = { viewModel.toggleFavorite(recipe.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteRecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
    onRemoveFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = recipe.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = recipe.description,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row {
                        Chip(
                            onClick = { },
                            colors = ChipDefaults.chipColors(
                                backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f)
                            )
                        ) {
                            Text(
                                text = recipe.category.name.replace("_", " "),
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                }
                
                IconButton(onClick = onRemoveFavorite) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Remove from Favorites",
                        tint = Color.Red
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Prep: ${recipe.prepTimeMinutes}m",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Cook: ${recipe.cookTimeMinutes}m",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Serves: ${recipe.servings}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = recipe.difficulty.name,
                    fontSize = 12.sp,
                    color = when (recipe.difficulty) {
                        com.recipeapp.model.Difficulty.EASY -> Color.Green
                        com.recipeapp.model.Difficulty.MEDIUM -> Color(0xFFFF9800)
                        com.recipeapp.model.Difficulty.HARD -> Color.Red
                    },
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}