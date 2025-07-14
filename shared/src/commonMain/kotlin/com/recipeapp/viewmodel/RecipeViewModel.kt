package com.recipeapp.viewmodel

import com.recipeapp.model.Recipe
import com.recipeapp.model.Category
import com.recipeapp.repository.RecipeRepository
import com.recipeapp.repository.RecipeRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val repository: RecipeRepository = RecipeRepositoryImpl()
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()
    
    private val _favoriteRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val favoriteRecipes: StateFlow<List<Recipe>> = _favoriteRecipes.asStateFlow()
    
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes.asStateFlow()
    
    init {
        loadRecipes()
        loadFavoriteRecipes()
    }
    
    private fun loadRecipes() {
        viewModelScope.launch {
            repository.getAllRecipes().collect { recipeList ->
                _recipes.value = recipeList
                updateFilteredRecipes()
            }
        }
    }
    
    private fun loadFavoriteRecipes() {
        viewModelScope.launch {
            repository.getFavoriteRecipes().collect { favoriteList ->
                _favoriteRecipes.value = favoriteList
            }
        }
    }
    
    fun searchRecipes(query: String) {
        _searchQuery.value = query
        updateFilteredRecipes()
    }
    
    fun filterByCategory(category: Category?) {
        _selectedCategory.value = category
        updateFilteredRecipes()
    }
    
    private fun updateFilteredRecipes() {
        val query = _searchQuery.value
        val category = _selectedCategory.value
        val allRecipes = _recipes.value
        
        var filtered = allRecipes
        
        if (category != null) {
            filtered = filtered.filter { it.category == category }
        }
        
        if (query.isNotBlank()) {
            filtered = filtered.filter { recipe ->
                recipe.title.contains(query, ignoreCase = true) ||
                recipe.description.contains(query, ignoreCase = true) ||
                recipe.ingredients.any { it.contains(query, ignoreCase = true) }
            }
        }
        
        _filteredRecipes.value = filtered
    }
    
    fun toggleFavorite(recipeId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(recipeId)
        }
    }
    
    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.addRecipe(recipe)
            _isLoading.value = false
        }
    }
    
    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.updateRecipe(recipe)
            _isLoading.value = false
        }
    }
    
    fun deleteRecipe(recipeId: String) {
        viewModelScope.launch {
            repository.deleteRecipe(recipeId)
        }
    }
    
    fun getRecipeById(id: String): Recipe? {
        return _recipes.value.find { it.id == id }
    }
}