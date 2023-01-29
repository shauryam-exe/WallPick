package com.code.wallpick.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.wallpick.data.auth.AuthRepository
import com.code.wallpick.data.auth.AuthState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    val authState by lazy { MutableLiveData<AuthState<FirebaseUser>>() }
    val authFlow = MutableStateFlow<AuthState<FirebaseUser>?>(null)

    fun login(email: String, password: String) = viewModelScope.launch {
        authState.value = AuthState.Loading
        Log.d("viewmodel", "${authState.value} before")
        val result = repository.login(email, password)
        Log.d("viewmodel", "$result after")
        authState.value = result
    }
}