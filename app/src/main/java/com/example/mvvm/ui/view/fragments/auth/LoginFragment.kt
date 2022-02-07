package com.example.mvvm.ui.view.fragments.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import com.example.mvvm.utils.handleApiError
import com.example.mvvm.utils.startNewActivity
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    @Inject
    lateinit var spManager: SharedPreferenceManager
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

        if (spManager.getRememberStatus()) {
            binding.userNameInputField.setText(spManager.getRememberUsername())
            binding.passwordInputField.setText(spManager.getRememberPassword())
            binding.cbRememberMe.isChecked = true
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            binding.progressBar.visible(it is Resource.Loading)
            binding.loginButton.enable(false)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        spManager.storeTokenInformation(it.value.data.token)
                        spManager.storeUserInformation(it.value.data.user)
                        spManager.isLoggedIn(true)
                        requireActivity().startNewActivity(DashboardActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) {
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

            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

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
            if (spManager.getRememberStatus()) {
                spManager.updateRememberUserCredential(userName, password)
            } else {
                spManager.rememberUserCredential(true, userName, password)
            }
        }

        viewModel.userLogin(userName, password)
    }
}