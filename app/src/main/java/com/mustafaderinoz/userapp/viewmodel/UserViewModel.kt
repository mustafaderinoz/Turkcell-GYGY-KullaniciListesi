package com.mustafaderinoz.userapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafaderinoz.userapp.data.model.User
import com.mustafaderinoz.userapp.data.repository.UserRepository
import com.mustafaderinoz.userapp.utils.toUserFriendlyMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// UI State tanımları
sealed interface UserUiState {
    object Loading : UserUiState
    data class Success(val users: List<User>) : UserUiState
    data class Error(val message: String) : UserUiState
}

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    // Arama sorgusu için ayrı StateFlow
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Tüm kullanıcıları cache'lemek için
    private var allUsers: List<User> = emptyList()

    // Yenileme durumu
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchUsers()
    }


    fun fetchUsers() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                val users = repository.getUsers()
                allUsers = users
                _uiState.value = UserUiState.Success(users)
            } catch (e: Exception) {

                _uiState.value = UserUiState.Error(e.toUserFriendlyMessage())
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val users = repository.getUsers()
                allUsers = users
                applySearch(_searchQuery.value, users)
            } catch (e: Exception) {

                _uiState.value = UserUiState.Error(e.toUserFriendlyMessage())
            } finally {
                _isRefreshing.value = false
            }
        }
    }
// ...

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        applySearch(query, allUsers)
    }

    private fun applySearch(query: String, users: List<User>) {
        if (query.isBlank()) {
            _uiState.value = UserUiState.Success(users)
        } else {
            val filtered = users.filter { user ->
                user.name.contains(query, ignoreCase = true) ||
                        user.email.contains(query, ignoreCase = true) ||
                        user.username.contains(query, ignoreCase = true)
            }
            _uiState.value = UserUiState.Success(filtered)
        }
    }
}