package com.recipeapp.repository

import com.recipeapp.model.Recipe
import com.recipeapp.model.Category
import com.recipeapp.model.Difficulty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    fun getRecipeById(id: String): Flow<Recipe?>
    fun getFavoriteRecipes(): Flow<List<Recipe>>
    fun getRecipesByCategory(category: Category): Flow<List<Recipe>>
    suspend fun addRecipe(recipe: Recipe)
    suspend fun updateRecipe(recipe: Recipe)
    suspend fun deleteRecipe(id: String)
    suspend fun toggleFavorite(id: String)
}

class RecipeRepositoryImpl : RecipeRepository {
    private val _recipes = MutableStateFlow(getSampleRecipes())
    
    override fun getAllRecipes(): Flow<List<Recipe>> = _recipes
    
    override fun getRecipeById(id: String): Flow<Recipe?> = 
        _recipes.map { recipes -> recipes.find { it.id == id } }
    
    override fun getFavoriteRecipes(): Flow<List<Recipe>> = 
        _recipes.map { recipes -> recipes.filter { it.isFavorite } }
    
    override fun getRecipesByCategory(category: Category): Flow<List<Recipe>> = 
        _recipes.map { recipes -> recipes.filter { it.category == category } }
    
    override suspend fun addRecipe(recipe: Recipe) {
        val currentRecipes = _recipes.value.toMutableList()
        currentRecipes.add(recipe)
        _recipes.value = currentRecipes
    }
    
    override suspend fun updateRecipe(recipe: Recipe) {
        val currentRecipes = _recipes.value.toMutableList()
        val index = currentRecipes.indexOfFirst { it.id == recipe.id }
        if (index != -1) {
            currentRecipes[index] = recipe
            _recipes.value = currentRecipes
        }
    }
    
    override suspend fun deleteRecipe(id: String) {
        val currentRecipes = _recipes.value.toMutableList()
        currentRecipes.removeAll { it.id == id }
        _recipes.value = currentRecipes
    }
    
    override suspend fun toggleFavorite(id: String) {
        val currentRecipes = _recipes.value.toMutableList()
        val index = currentRecipes.indexOfFirst { it.id == id }
        if (index != -1) {
            currentRecipes[index] = currentRecipes[index].copy(isFavorite = !currentRecipes[index].isFavorite)
            _recipes.value = currentRecipes
        }
    }
}

private fun getSampleRecipes(): List<Recipe> = listOf(
    Recipe(
        id = "1",
        title = "Classic Chocolate Chip Cookies",
        description = "Delicious homemade chocolate chip cookies that are crispy on the outside and chewy on the inside.",
        ingredients = listOf(
            "2¼ cups all-purpose flour",
            "1 tsp baking soda",
            "1 tsp salt",
            "1 cup butter, softened",
            "¾ cup granulated sugar",
            "¾ cup packed brown sugar",
            "2 large eggs",
            "2 tsp vanilla extract",
            "2 cups chocolate chips"
        ),
        instructions = listOf(
            "Preheat oven to 375°F (190°C)",
            "Mix flour, baking soda, and salt in a bowl",
            "Cream butter and sugars until light and fluffy",
            "Beat in eggs and vanilla",
            "Gradually add flour mixture",
            "Stir in chocolate chips",
            "Drop rounded tablespoons onto ungreased cookie sheets",
            "Bake 9-11 minutes until golden brown"
        ),
        prepTimeMinutes = 15,
        cookTimeMinutes = 10,
        servings = 48,
        difficulty = Difficulty.EASY,
        category = Category.DESSERT
    ),
    Recipe(
        id = "2",
        title = "Grilled Chicken Caesar Salad",
        description = "Fresh romaine lettuce with grilled chicken, parmesan cheese, and homemade Caesar dressing.",
        ingredients = listOf(
            "2 chicken breasts",
            "1 large head romaine lettuce",
            "½ cup parmesan cheese, grated",
            "¼ cup olive oil",
            "2 cloves garlic, minced",
            "2 anchovy fillets",
            "1 lemon, juiced",
            "1 tsp Worcestershire sauce",
            "1 egg yolk",
            "Croutons for serving"
        ),
        instructions = listOf(
            "Season chicken with salt and pepper",
            "Grill chicken for 6-7 minutes per side",
            "Let chicken rest, then slice",
            "Wash and chop romaine lettuce",
            "Make dressing by whisking olive oil, garlic, anchovies, lemon juice, Worcestershire, and egg yolk",
            "Toss lettuce with dressing",
            "Top with chicken, parmesan, and croutons"
        ),
        prepTimeMinutes = 20,
        cookTimeMinutes = 15,
        servings = 4,
        difficulty = Difficulty.MEDIUM,
        category = Category.SALAD
    ),
    Recipe(
        id = "3",
        title = "Homemade Pizza Margherita",
        description = "Classic Italian pizza with fresh mozzarella, tomatoes, and basil on homemade dough.",
        ingredients = listOf(
            "1 pizza dough ball",
            "½ cup pizza sauce",
            "8 oz fresh mozzarella, sliced",
            "2 large tomatoes, sliced",
            "Fresh basil leaves",
            "2 tbsp olive oil",
            "Salt and pepper to taste"
        ),
        instructions = listOf(
            "Preheat oven to 500°F (260°C)",
            "Roll out pizza dough on floured surface",
            "Transfer to pizza stone or baking sheet",
            "Spread sauce evenly over dough",
            "Add mozzarella and tomato slices",
            "Drizzle with olive oil, season with salt and pepper",
            "Bake for 10-12 minutes until crust is golden",
            "Top with fresh basil before serving"
        ),
        prepTimeMinutes = 30,
        cookTimeMinutes = 12,
        servings = 4,
        difficulty = Difficulty.MEDIUM,
        category = Category.MAIN_COURSE
    )
)