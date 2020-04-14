package com.uala.recetas.data.repository

import com.uala.recetas.data.UseCaseResult
import com.uala.recetas.data.service.RecipeApi
import com.uala.recetas.domain.Meal

interface RecipesRepository {
    suspend fun getRecipes(apiQuery: String): UseCaseResult<List<Meal>>
}

class RecipeRepositoryImpl(private val recipeApi: RecipeApi) : RecipesRepository {
    override suspend fun getRecipes(apiQuery: String): UseCaseResult<List<Meal>> {
        return try {
            val result = recipeApi.searchRecipeAsync(apiQuery).await()
            UseCaseResult.Success(result.meals)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

}
