package com.uala.recetas.presentation.detail

import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.gson.Gson
import com.uala.recetas.R
import com.uala.recetas.domain.Meal
import kotlinx.android.synthetic.main.activity_recipe_detail.*

class MealDetailActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private val MEAL = "meal"
    private var meal: Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        back_pressed.setOnClickListener {
            onBackPressed()
        }
        setMeal()
    }

    override fun onStart() {
        super.onStart()
        youtube_view.initialize(getString(R.string.API_KEY), this)
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

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        youTubePlayer?.fullscreenControlFlags = YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION or
                YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE

        if (!wasRestored) {
            val string = meal?.strYoutube?.split("=")
            youTubePlayer?.cueVideo(string?.get(1))
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        error: YouTubeInitializationResult?
    ) {
        val REQUEST_CODE = 1

        if (error!!.isUserRecoverableError) {
            error.getErrorDialog(this, REQUEST_CODE).show()
        } else {
            val errorMessage = java.lang.String.format(
                "There was an error initializing the YoutubePlayer (%1\$s)",
                error.toString()
            )
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

}
