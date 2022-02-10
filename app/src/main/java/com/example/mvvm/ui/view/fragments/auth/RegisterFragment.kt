package com.example.mvvm.ui.view.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.mvvm.R
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}