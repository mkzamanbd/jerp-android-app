package com.example.simplelogin.ui.view.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.simplelogin.R
import com.example.simplelogin.base.BaseFragment
import com.example.simplelogin.databinding.FragmentLoginBinding
import com.example.simplelogin.network.AuthApi
import com.example.simplelogin.network.Resource
import com.example.simplelogin.repository.AuthRepository
import com.example.simplelogin.ui.view.DashboardActivity
import com.example.simplelogin.utils.enable
import com.example.simplelogin.utils.handleApiError
import com.example.simplelogin.utils.startNewActivity
import com.example.simplelogin.utils.visible
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)
        binding.loginButton.enable(false)


        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.loginButton.enable(true)
                    lifecycleScope.launch {
                        viewModel.saveAccessToken(it.value.token)
                        requireActivity().startNewActivity(DashboardActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) {
                    login()
                    binding.loginButton.enable(true)
                }
            }
        })

        binding.passwordInputField.addTextChangedListener {
            val email = binding.emailInputField.text.toString().trim()
            binding.loginButton.enable(email.isNotEmpty() && it.toString().isNotEmpty())
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
        val email = binding.emailInputField.text.toString().trim()
        val password = binding.passwordInputField.text.toString().trim()

        viewModel.userLogin(email, password)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
}