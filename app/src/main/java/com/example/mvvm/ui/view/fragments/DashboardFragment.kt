package com.example.mvvm.ui.view.fragments

import android.os.Bundle
import android.view.View
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.databinding.FragmentDashboardBinding


class DashboardFragment : BaseFragment<FragmentDashboardBinding>(
    FragmentDashboardBinding::inflate
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