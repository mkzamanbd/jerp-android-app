package com.example.mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.adapter.UserListAdapter
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.databinding.FragmentUserBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.viewModel.UserViewModel
import com.example.mvvm.utils.handleApiError
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(
    FragmentUserBinding::inflate
), UserListAdapter.OnItemClickListener {

    private val viewModel by viewModels<UserViewModel>()
    private val userListAdapter = UserListAdapter(arrayListOf(), this)
    lateinit var userListRecyclerView: RecyclerView

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
                    Log.d("usersList", it.value.users.toString())
                    userListAdapter.updateUsers(it.value.users)
                }
                is Resource.Failure -> handleApiError(it) {
                    getUsersList()
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        })
    }

    private fun getUsersList() {
        viewModel.getUsers()
    }

    override fun onItemClick(position: Int) {
        userListAdapter.notifyItemChanged(position)
        Toast.makeText(requireContext(),
            userListAdapter.users[position].toString(),
            Toast.LENGTH_SHORT).show()
    }
}