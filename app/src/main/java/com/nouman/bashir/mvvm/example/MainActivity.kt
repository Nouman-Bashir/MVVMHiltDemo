package com.nouman.bashir.mvvm.example

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nouman.bashir.mvvm.example.adapters.UserAdapter
import com.nouman.bashir.mvvm.example.databinding.ActivityMainBinding
import com.nouman.bashir.mvvm.example.models.UIState
import com.nouman.bashir.mvvm.example.models.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivityTg"
    private val viewModel:UserViewModel by viewModels()
    private lateinit var adapter:UserAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = UserAdapter()
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{
                    state->
                    when (state) {
                        is UIState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.errorText.visibility = View.GONE
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                        is UIState.Success -> {
                            adapter.submitList(state.users)
                            binding.progressBar.visibility = View.GONE
                            binding.errorText.visibility = View.GONE
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                        is UIState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorText.text = state.message
                            binding.errorText.visibility = View.VISIBLE
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }

            }
        }

    }
}