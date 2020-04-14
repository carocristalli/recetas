package com.uala.recetas.presentation.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.uala.recetas.R
import com.uala.recetas.domain.Meal
import kotlinx.android.synthetic.main.activity_recipe_detail.*

class MealDetailActivity: AppCompatActivity() {

    private val MEAL = "meal"
    private var meal: Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setMeal()
    }

    private fun setMeal() {
        meal = Gson().fromJson(intent?.getStringExtra(MEAL), Meal::class.java)

        Glide.with(this)
            .load(meal?.strMealThumb)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .thumbnail()
            .into(strMealThumb)

        strMeal.text = meal?.strMeal
        strInstructions.text = meal?.strInstructions
        strIngredient.text = meal?.ingredients()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
