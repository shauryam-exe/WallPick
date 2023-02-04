package com.code.wallpick.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.wallpick.data.auth.AuthRepository
import com.code.wallpick.data.auth.AuthState
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    val authState by lazy { MutableLiveData<AuthState<FirebaseUser>>() }

    fun login(credential: AuthCredential) = viewModelScope.launch {
        authState.value = AuthState.Loading
        val result = repository.login(credential)
        authState.value = result
    }

    fun loginAnonymous() = viewModelScope.launch {
        authState.value = AuthState.Loading
        val result = repository.loginAnonymous()
        authState.value = result
    }
}