package com.uala.recetas.presentation.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uala.recetas.R

class RecipesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    RecipesFragment.newInstance()
                )
                .commitNow()
        }
    }
}
