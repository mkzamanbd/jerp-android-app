package com.example.mvvm.ui.view.fragments.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mvvm.R
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.databinding.FragmentLoginBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.view.activities.DashboardActivity
import com.example.mvvm.ui.viewModel.AuthViewModel
import com.example.mvvm.utils.enable
import com.example.mvvm.utils.handleFragmentApiError
import com.example.mvvm.utils.startNewActivityAnimation
import com.example.mvvm.utils.visible
import com.example.mvvm.utils.hideSoftKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    @Inject
    lateinit var prefManager: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)
        if (binding.passwordInputField.text.isNullOrEmpty()) {
            binding.loginButton.enable(false)
        }

        if (prefManager.isRemembered()) {
            binding.userNameInputField.setText(prefManager.getRememberUsername())
            binding.passwordInputField.setText(prefManager.getRememberPassword())
            binding.cbRememberMe.isChecked = true
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            binding.progressBar.visible(it is Resource.Loading)
            binding.loginButton.enable(false)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        prefManager.setLoginStatus(true)
                        prefManager.setTokenInformation(it.value.data.token)
                        prefManager.setUserInformation(it.value.data.user)
                        mActivity.startNewActivityAnimation(DashboardActivity::class.java)
                    }
                }
                is Resource.Failure -> handleFragmentApiError(it) {
                    login()
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        }

        binding.passwordInputField.addTextChangedListener {
            val userName = binding.userNameInputField.text.toString().trim()
            binding.loginButton.enable(userName.isNotEmpty() && it.toString().isNotEmpty())
        }


        binding.loginButton.setOnClickListener {
            login()
            hideSoftKeyboard(mContext, binding.passwordInputField)
        }

        binding.createAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun login() {
        binding.tvErrorMessage.visible(false)
        binding.loginButton.enable(false)
        val userName = binding.userNameInputField.text.toString().trim()
        val password = binding.passwordInputField.text.toString().trim()

        val rememberMe = binding.cbRememberMe
        if (rememberMe.isChecked) {
            if (prefManager.isRemembered()) {
                prefManager.updateRememberUserCredential(userName, password)
            } else {
                prefManager.rememberUserCredential(true, userName, password)
            }
        }

        viewModel.userLogin(userName, password)
    }
}