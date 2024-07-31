package com.example.recipeapp.home.favorite.repo

import com.example.recipeapp.data.local.LocalDataSource
import com.example.recipeapp.data.local.model.UserMealCrossRef
import com.example.recipeapp.data.local.model.UserWithMeal
import com.example.recipeapp.data.remote.RemoteDataSource
import com.example.recipeapp.data.remote.dto.Meal
import com.example.recipeapp.data.remote.dto.MealList

class FavRepoImpl(private val localDataSource: LocalDataSource,private val remoteDataSource: RemoteDataSource) : FavRepo {

    override suspend fun getAllUserFavMeals(userId : Int): List<Meal>{
        return localDataSource.getAllUserFavMeals(userId)
    }

    override suspend fun deleteFromFav(userWithMeals: UserMealCrossRef) {
        return localDataSource.deleteFromFav(userWithMeals)
    }

    override suspend fun getMealById(id: String): MealList {
        return remoteDataSource.getMealById(id)
    }

}