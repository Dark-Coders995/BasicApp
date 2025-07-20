package com.example.basicapp.data.paging



import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.basicapp.data.model.User
import com.example.basicapp.data.repository.UserRepository


class UserPagingSource(
    private val repository: UserRepository
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val users = repository.getUsers(page, params.loadSize)

            LoadResult.Page(
                data = users,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (users.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}