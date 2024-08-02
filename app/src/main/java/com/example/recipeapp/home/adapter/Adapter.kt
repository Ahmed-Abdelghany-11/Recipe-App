package com.example.recipeapp.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.data.SharedPreference.AuthSharedPref
import com.example.recipeapp.data.local.model.UserMealCrossRef
import com.example.recipeapp.data.remote.dto.Meal
import com.example.recipeapp.home.viewModel.HomeViewModel



class Adapter(private val meal: List<Meal?>?,private val viewModel : HomeViewModel) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    var myListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_recipe, parent, false)
        return ViewHolder(view, myListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = meal?.get(position)
        holder.getTextView().text = meal?.strMeal
        Glide.with(holder.itemView.context)
            .load(meal?.strMealThumb)
            .placeholder(R.drawable.baseline_image_24)
            .circleCrop()
            .into(holder.getImageView())

        holder.getFavButton().setOnClickListener {
             addFavMeal(holder.itemView.context,meal!!)
            holder.getFavButton().setImageResource(R.drawable.baseline_favorite_24)
        }
    }

    override fun getItemCount() = meal?.size ?: 0


    class ViewHolder(private var view: View, listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(view) {
        private var textView: TextView? = null
        private var imageView: ImageView? = null
        private var favbtn: ImageButton? = null


        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

        fun getTextView(): TextView {
            return textView ?: view.findViewById(R.id.textRecipe)
        }

        fun getImageView(): ImageView {
            return imageView ?: view.findViewById(R.id.imageRecipe)
        }

        fun getFavButton(): ImageButton {
            return favbtn ?: view.findViewById(R.id.fav)
        }

    }

    private fun addFavMeal(context: Context,meal: Meal){
        val userId= AuthSharedPref(context).getUserId()
        viewModel.insertMeal(meal)
        viewModel.insertIntoFav(
            userMealCrossRef = UserMealCrossRef(
                userId,
                meal.idMeal
            )
        )
    }

}