package com.example.basicapp


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicapp.databinding.ActivityMainBinding
import com.example.basicapp.ui.adapter.UserAdapter
import com.example.basicapp.ui.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()
    private val userAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSwipeRefresh()
        observeUsers()
        observeLoadStates()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }
    }

    private fun observeUsers() {
        lifecycleScope.launch {
            viewModel.users.collectLatest { pagingData ->
                userAdapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadStates() {
        lifecycleScope.launch {
            userAdapter.loadStateFlow.collectLatest { loadStates ->
                // Handle initial load state
                when (loadStates.refresh) {
                    is LoadState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.tvError.visibility = View.GONE
                    }
                    is LoadState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    is LoadState.NotLoading -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.GONE
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                }

                when (loadStates.append) {
                    is LoadState.Loading -> {

                    }
                    is LoadState.Error -> {
                    }
                    is LoadState.NotLoading -> {

                    }
                }
            }
        }
    }

    private fun refreshData() {
        userAdapter.refresh()
    }
}