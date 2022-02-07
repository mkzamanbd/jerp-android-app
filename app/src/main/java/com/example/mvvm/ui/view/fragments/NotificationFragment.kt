package com.example.mvvm.ui.view.fragments

import android.os.Bundle
import android.view.View
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.databinding.FragmentNotificationBinding

class NotificationFragment : BaseFragment<FragmentNotificationBinding>(
    FragmentNotificationBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }
}