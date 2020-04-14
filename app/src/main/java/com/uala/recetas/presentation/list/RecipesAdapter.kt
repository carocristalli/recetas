package com.uala.recetas.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.uala.recetas.R
import com.uala.recetas.domain.Meal
import kotlinx.android.synthetic.main.recipe_view_item.view.*
import kotlin.properties.Delegates

class RecipesAdapter(private val mealClickListener: OnMealClickListener) : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    var meals: List<Meal> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_view_item, parent, false)
        val holder = RecipeViewHolder(view)
        holder.itemView.container.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                mealClickListener.onMealClickListener(holder.adapterPosition)
            }
        }
        return holder
    }

    override fun getItemCount(): Int = meals.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val meal: Meal = meals[position]
            holder.bind(meal)
        }
    }

    fun updateData(newList: List<Meal>) {
        meals = newList
    }

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(meal: Meal) {
            Glide.with(itemView.context)
                .load(meal.strMealThumb)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .thumbnail()
                .into(itemView.strMealThumb)

            itemView.strMeal.text = meal.strMeal
            itemView.strCategory.text = meal.strCategory
        }
    }

    interface OnMealClickListener{
        fun onMealClickListener(position: Int)
    }

}
