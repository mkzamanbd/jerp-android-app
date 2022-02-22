package me.kzaman.android.ui.view.fragments.auth

import android.os.Bundle
import android.view.View
import me.kzaman.android.R
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override val layoutId: Int = R.layout.fragment_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }
}