package com.example.simplelogin.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.simplelogin.base.BaseFragment
import com.example.simplelogin.data.model.User
import com.example.simplelogin.databinding.FragmentProfileBinding
import com.example.simplelogin.network.Resource
import com.example.simplelogin.network.UserApi
import com.example.simplelogin.repository.ProfileRepository
import com.example.simplelogin.ui.viewModel.ProfileViewModel
import com.example.simplelogin.utils.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, ProfileRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)
        binding.profileInfo.visible(false)

        viewModel.getUser()

        viewModel.profile.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    binding.progressBar.visible(false)
                    binding.profileInfo.visible(true)
                    updateUI(it.value.user)
                }
                is Resource.Loading -> {
                    binding.progressBar.visible(true)
                }
            }
        })

        binding.userLogout.setOnClickListener{
            logout()
        }

    }

    private fun updateUI(user: User){
        binding.tvName.text = user.name
        binding.tvEmail.text = user.email
        Log.d("user", user.toString())
    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): ProfileRepository {
        val token = runBlocking { userPreferences.accessToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java, token)

        return ProfileRepository(api)
    }
}

