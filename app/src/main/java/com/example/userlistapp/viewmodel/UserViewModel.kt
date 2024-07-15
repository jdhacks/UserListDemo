package com.example.userlistapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlistapp.data.User
import com.example.userlistapp.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val currentUsers = mutableListOf<User>()

    fun fetchUsers() {
        viewModelScope.launch {
            val users = userRepository.getUsers()
            currentUsers.addAll(users)
            _users.value = currentUsers
        }
    }

    fun fetchNextPageUsers() {
        viewModelScope.launch {
            val nextPageUsers = userRepository.getNextPageUsers()
            nextPageUsers?.let {
                currentUsers.addAll(it)
                _users.value = currentUsers
            }
        }
    }
}
