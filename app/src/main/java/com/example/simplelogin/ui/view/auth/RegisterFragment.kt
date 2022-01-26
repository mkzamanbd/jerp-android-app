package com.example.simplelogin.ui.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.simplelogin.R
import com.example.simplelogin.base.BaseFragment
import com.example.simplelogin.databinding.FragmentRegisterBinding
import com.example.simplelogin.network.AuthApi
import com.example.simplelogin.repository.AuthRepository

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}