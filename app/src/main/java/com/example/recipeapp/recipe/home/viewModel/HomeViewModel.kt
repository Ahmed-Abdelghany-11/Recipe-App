package com.example.recipeapp.recipe.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.local.model.UserMealCrossRef
import com.example.recipeapp.data.local.model.UserWithMeal
import com.example.recipeapp.data.remote.dto.CategoryList
import com.example.recipeapp.data.remote.dto.Meal
import com.example.recipeapp.data.remote.dto.MealList
import com.example.recipeapp.recipe.home.repo.RetrofitRepoImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val myRepo: RetrofitRepoImp) : ViewModel() {
    private val _getMyResponse = MutableLiveData<MealList?>()
    val getMyResponse: LiveData<MealList?> = _getMyResponse

    private val _getMealsByLetterResponse = MutableLiveData<MealList?>()
    val getMealsByLetterResponse: LiveData<MealList?> = _getMealsByLetterResponse

    private val _getAllCategoriesResponse = MutableLiveData<CategoryList>()
    val getAllCategoriesResponse: LiveData<CategoryList> = _getAllCategoriesResponse

    private val _userFavMeals = MutableLiveData<UserWithMeal?>()
    val userFavMeals: LiveData<UserWithMeal?> get() = _userFavMeals

    private val _isFavoriteMeal = MutableLiveData<Boolean>()
    val isFavoriteMeal: LiveData<Boolean> get() = _isFavoriteMeal


    fun getMyResponse() {
        viewModelScope.launch {
            try {
                _getMyResponse.postValue(myRepo.getMyResponse())
            } catch (e: Exception) {
                Log.d("Exception", e.printStackTrace().toString())
            }
        }
    }

    fun getMealsByRandomLetter() {
        try {
            viewModelScope.launch {
                fetchMealsByRandomLetter()
            }
        } catch (e: Exception) {
            Log.d("Exception", e.printStackTrace().toString())
        }

    }

    private suspend fun fetchMealsByRandomLetter() {
        val randomLetter = ('a'..'z').random()
        val response = myRepo.getMealByFirstLetter(randomLetter.toString())
        if (response?.meals.isNullOrEmpty()) {
            fetchMealsByRandomLetter()
        } else {
            try {
                _getMealsByLetterResponse.postValue(response)
            } catch (e: Exception) {
                Log.d("Exception", e.printStackTrace().toString())
            }
        }
    }

        fun getAllCategories() {
            try {
                viewModelScope.launch {
                    _getAllCategoriesResponse.postValue(myRepo.getAllCategories())
                }
            } catch (e: Exception) {
                Log.d("Exception", e.printStackTrace().toString())
            }
        }

        fun insertIntoFav(userMealCrossRef: UserMealCrossRef) {
            viewModelScope.launch {
                myRepo.insertIntoFav(userMealCrossRef)
            }
        }

        fun insertMeal(meal: Meal) =
            viewModelScope.launch {
                myRepo.insertMeal(meal)
            }

        fun deleteMeal(meal: Meal) =
            viewModelScope.launch {
                myRepo.deleteMeal(meal)
            }

        fun deleteFromFav(userMealCrossRef: UserMealCrossRef) {
            viewModelScope.launch(Dispatchers.IO) {
                myRepo.deleteFromFav(userMealCrossRef)
            }
        }

        fun isFavoriteMeal(userId: Int, mealId: String): LiveData<Boolean> {
            val isFavorite = MutableLiveData<Boolean>()
            viewModelScope.launch {
                isFavorite.postValue(myRepo.isFavoriteMeal(userId, mealId))
            }
            return isFavorite
        }

        fun gerUserWithMeals(userId: Int) {
            viewModelScope.launch {
                _userFavMeals.postValue(myRepo.getUserWithMeals(userId))
            }
        }
    }