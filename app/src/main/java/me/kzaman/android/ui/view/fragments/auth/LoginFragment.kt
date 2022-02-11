package me.kzaman.android.ui.view.fragments.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import me.kzaman.android.R
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.database.SharedPreferenceManager
import me.kzaman.android.databinding.FragmentLoginBinding
import me.kzaman.android.network.Resource
import me.kzaman.android.ui.view.activities.DashboardActivity
import me.kzaman.android.ui.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.utils.LoadingUtils
import me.kzaman.android.utils.enable
import me.kzaman.android.utils.startNewActivityAnimation
import me.kzaman.android.utils.visible
import me.kzaman.android.utils.handleFragmentApiError
import me.kzaman.android.utils.hideSoftKeyboard
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

        loadingUtils = LoadingUtils(mContext)

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
            if (it !is Resource.Loading) {
                binding.loginButton.isEnabled = true
                binding.loginButton.alpha = 1f
            }
            when (it) {
                is Resource.Success -> {
                    val response = it.value
                    if (response.responseCode == 200) {
                        if (response.data.user == null || response.data.userArea == null) {
                            binding.tvErrorMessage.visibility = View.VISIBLE
                            return@observe
                        }
                        prefManager.setLoginStatus(true)
                        prefManager.setTokenInformation(response.data.token)
                        response.data.user?.let { user -> prefManager.setUserInformation(user) }
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