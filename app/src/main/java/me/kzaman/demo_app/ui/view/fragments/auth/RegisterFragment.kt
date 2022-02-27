package me.kzaman.demo_app.ui.view.fragments.auth

import android.os.Bundle
import me.kzaman.demo_app.R
import me.kzaman.demo_app.base.BaseFragment
import me.kzaman.demo_app.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override val layoutId: Int = R.layout.fragment_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }
}