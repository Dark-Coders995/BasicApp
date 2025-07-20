package com.example.basicapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.basicapp.data.model.User
import com.example.basicapp.data.paging.UserPagingSource
import com.example.basicapp.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    val users: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory = {
            UserPagingSource(repository)
        }
    ).flow.cachedIn(viewModelScope)
}