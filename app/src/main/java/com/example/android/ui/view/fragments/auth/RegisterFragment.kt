package com.example.android.ui.view.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.android.R
import com.example.android.base.BaseFragment
import com.example.android.databinding.FragmentRegisterBinding

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