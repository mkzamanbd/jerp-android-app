package com.example.android.ui.view.fragments

import android.os.Bundle
import android.view.View
import com.example.android.base.BaseFragment
import com.example.android.databinding.FragmentNotificationBinding

class NotificationFragment : BaseFragment<FragmentNotificationBinding>(
    FragmentNotificationBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }
}