package com.uala.recetas.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uala.recetas.data.UseCaseResult
import com.uala.recetas.data.repository.RecipesRepository
import com.uala.recetas.domain.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipesViewModel(private val recipeRepository: RecipesRepository) : ViewModel() {

    val queryLiveData = MutableLiveData<String>()
    val showLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<String>()
    val mealList = MutableLiveData<List<Meal>>()

    fun loadRecipes() {
        showLoading.value = true
        viewModelScope.launch {
            val result =
                withContext(Dispatchers.IO) { recipeRepository.getRecipes(queryLiveData.value!!) }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> mealList.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun searchMeal(query: String) {
        queryLiveData.postValue(query)
    }
}
