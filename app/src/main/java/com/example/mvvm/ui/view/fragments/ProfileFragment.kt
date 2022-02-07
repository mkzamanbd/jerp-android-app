package com.example.mvvm.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.databinding.FragmentProfileBinding
import com.example.mvvm.ui.viewModel.ProfileViewModel
import com.example.mvvm.utils.logout
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    @Inject
    lateinit var prefManager: SharedPreferenceManager
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
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
        binding.tvName.text = prefManager.getUserFullName()
        binding.tvEmail.text = prefManager.getUserRoleName()
    }

    private fun getUser() {
        viewModel.getUser()
    }
}

