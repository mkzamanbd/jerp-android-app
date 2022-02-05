package com.example.mvvm.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.data.model.User
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.databinding.FragmentProfileBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.viewModel.ProfileViewModel
import com.example.mvvm.utils.handleApiError
import com.example.mvvm.utils.logout
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
        spManager = SharedPreferenceManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)

        updateUI()

        binding.userLogout.setOnClickListener {
            logout()
        }

    }

    private fun updateUI() {
        binding.tvName.text = spManager.getUserFullName()
        binding.tvEmail.text = spManager.getUserRoleName()
    }

    private fun getUser() {
        viewModel.getUser()
    }
}

