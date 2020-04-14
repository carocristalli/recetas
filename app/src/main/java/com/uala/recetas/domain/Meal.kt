package com.uala.recetas.domain

class Meal {

    val strMeal: String? = null
    val strCategory: String? = null
    val strInstructions: String? = null
    val strMealThumb: String? = null
    val strYoutube: String? = null
    val strIngredient1: String? = null
    val strIngredient2: String? = null
    val strIngredient3: String? = null
    val strIngredient4: String? = null
    val strIngredient5: String? = null
    val strIngredient6: String? = null
    val strIngredient7: String? = null
    val strIngredient8: String? = null
    val strIngredient9: String? = null
    val strIngredient10: String? = null
    val strIngredient11: String? = null
    val strIngredient12: String? = null
    val strIngredient13: String? = null
    val strIngredient14: String? = null
    val strIngredient15: String? = null
    val strIngredient16: String? = null
    val strIngredient17: String? = null
    val strIngredient18: String? = null
    val strIngredient19: String? = null
    val strIngredient20: String? = null

    fun ingredients() : String {
        var ingredients = ""
        val listIngredients =
            arrayOf(strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
                    strIngredient6,strIngredient7, strIngredient8, strIngredient9, strIngredient10,
                    strIngredient10, strIngredient11,strIngredient12, strIngredient13, strIngredient14, strIngredient15,
                    strIngredient16, strIngredient17,strIngredient18,strIngredient19, strIngredient20)

        for (ingredient in listIngredients){
            if(ingredient?.isNotEmpty()!!){
                ingredients = "$ingredients - $ingredient"
            }
        }

        return ingredients.substring(2, ingredients.length)
    }
}
