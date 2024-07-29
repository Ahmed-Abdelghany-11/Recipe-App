package com.example.recipeapp.authentication.signUp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.authentication.signUp.repo.SignUpRepository
import com.example.recipeapp.data.local.model.UserData
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpRepository: SignUpRepository,
) : ViewModel() {

    private val _isEmailExists = MutableLiveData<Boolean>()
    val isEmailExists:LiveData<Boolean>
        get() = _isEmailExists

    fun insertUser(userData: UserData) =
        viewModelScope.launch {
            signUpRepository.insertUserData(userData)
        }

    fun isEmailAlreadyExists(email: String) =
        viewModelScope.launch {
            _isEmailExists.postValue(signUpRepository.isEmailAlreadyExists(email))
        }

}