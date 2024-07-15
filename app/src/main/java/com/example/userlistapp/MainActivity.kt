package com.example.userlistapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userlistapp.adapter.UserAdapter
import com.example.userlistapp.api.ApiService
import com.example.userlistapp.databinding.ActivityMainBinding
import com.example.userlistapp.repository.UserRepository
import com.example.userlistapp.viewmodel.UserViewModel
import com.example.userlistapp.viewmodel.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val apiService by lazy { ApiService.create() }
    private val userRepository by lazy { UserRepository(apiService) }
    private val viewModelFactory by lazy { UserViewModelFactory(userRepository) }
    private val userViewModel: UserViewModel by viewModels { viewModelFactory }
    private lateinit var userAdapter: UserAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        userAdapter = UserAdapter(listOf())
        binding.recyclerView.adapter = userAdapter

        userViewModel.users.observe(this) { users ->
            userAdapter.updateUsers(users)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (lastVisibleItem + 5 >= totalItemCount) {
                    userViewModel.fetchNextPageUsers()
                }
            }
        })

        userViewModel.fetchUsers()
    }
}
