package com.example.mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.adapter.UserListAdapter
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.data.model.User
import com.example.mvvm.databinding.FragmentUserBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.viewModel.UserViewModel
import com.example.mvvm.utils.handleApiError
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(
    FragmentUserBinding::inflate
) {
    private val viewModel by viewModels<UserViewModel>()
    val userListAdapter = UserListAdapter(arrayListOf())
    lateinit var userListRecyclerView : RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userListRecyclerView = view.findViewById(R.id.userList)

        userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }

        getUsersList()

        viewModel.users.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visible(false)
                    Log.d("usersList", it.value.users.toString())
                    userListAdapter.updateUsers(it.value.users)
                }
                is Resource.Failure -> handleApiError(it){
                    binding.progressBar.visible(false)
                    getUsersList()
                }
            }
        })
    }

    private fun getUsersList(){
        viewModel.getUsers()
    }
}