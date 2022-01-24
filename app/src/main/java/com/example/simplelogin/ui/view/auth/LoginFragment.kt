package com.example.simplelogin.ui.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.simplelogin.base.BaseFragment
import com.example.simplelogin.databinding.FragmentLoginBinding
import com.example.simplelogin.network.AuthApi
import com.example.simplelogin.network.Resource
import com.example.simplelogin.repository.AuthRepository
import com.example.simplelogin.ui.view.DashboardActivity
import com.example.simplelogin.utils.enable
import com.example.simplelogin.utils.startNewActivity
import com.example.simplelogin.utils.visible
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar.visible(false)
        binding.loginButton.enable(false)


        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    viewModel.saveAuthToken(it.value.token)
                    requireActivity().startNewActivity(DashboardActivity::class.java)
                }
                is Resource.Failure -> {
                    binding.progressBar.visible(false)
                    binding.tvErrorMessage.visible(true)
                    Toast.makeText(requireContext(), "Login Failure!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.passwordInputField.addTextChangedListener{
            val email = binding.emailInputField.text.toString().trim()
            binding.loginButton.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.loginButton.setOnClickListener {
            binding.tvErrorMessage.visible(false)
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()

            // TODO validation

            binding.progressBar.visible(true)

            viewModel.userLogin(email, password)

        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
}