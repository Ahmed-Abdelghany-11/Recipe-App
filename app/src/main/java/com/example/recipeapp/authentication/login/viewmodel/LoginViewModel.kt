package com.example.recipeapp.authentication.login.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.authentication.login.repo.LoginRepository
import com.example.recipeapp.data.SharedPreference.AuthSharedPref
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val context: Context
) : ViewModel() {

    // first check email
    private val _isEmailExists = MutableLiveData<Boolean>()
    val isEmailExists: LiveData<Boolean>
        get() = _isEmailExists


    private val _isUserExists = MutableLiveData<Boolean>()
    val isUserExists: LiveData<Boolean>
        get() = _isUserExists




    fun isUserExists(email: String, password: String) =
        viewModelScope.launch {
            _isUserExists.postValue(loginRepository.isUserExists(email, password))
        }


    fun isEmailAlreadyExists(email: String) =
        viewModelScope.launch {
            _isEmailExists.postValue(loginRepository.isEmailAlreadyExists(email))
        }



    fun saveUserIdInSharedPref(email: String, password: String){
        viewModelScope.launch {
            val id:Int = loginRepository.getUserIdByEmailAndPassword(email, password)
            AuthSharedPref(context).saveUserId(id)
            Log.d("userrid",id.toString())
        }
    }


}