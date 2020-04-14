package com.uala.recetas.data.service

import com.uala.recetas.domain.RecipeResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("/api/json/v1/1/search.php")
    fun searchRecipeAsync(
        @Query("s") query: String
    ): Deferred<RecipeResponse>

    @GET("/api/json/v1/1/random.php")
    fun searchRandomMealAsync(): Deferred<RecipeResponse>
}
