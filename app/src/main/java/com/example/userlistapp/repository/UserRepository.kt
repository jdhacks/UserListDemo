package com.example.userlistapp.repository

import com.example.userlistapp.api.ApiService
import com.example.userlistapp.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val apiService: ApiService) {
    private var currentPage = 1
    private var totalPages = 1

    suspend fun getUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getUsers(currentPage)
            totalPages = response.total_pages
            response.data
        }
    }

    suspend fun getNextPageUsers(): List<User>? {
        return if (currentPage < totalPages) {
            withContext(Dispatchers.IO) {
                currentPage++
                val response = apiService.getUsers(currentPage)
                response.data
            }
        } else {
            null
        }
    }
}
