package com.nouman.bashir.mvvm.example.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nouman.bashir.mvvm.example.repos.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class UIState {
    object Loading : UIState()
    data class Success(val users: List<User>) : UIState()
    data class Error(val message: String) : UIState()
}

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            delay(1000)
            try {
                val users = repository.fetchUsers()
                _uiState.value = UIState.Success(users)
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message?:"Unknown Error")
            }
        }

    }

}