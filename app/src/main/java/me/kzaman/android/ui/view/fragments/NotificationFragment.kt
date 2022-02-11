package me.kzaman.android.ui.view.fragments

import android.os.Bundle
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.databinding.FragmentNotificationBinding

class NotificationFragment : BaseFragment<FragmentNotificationBinding>(
    FragmentNotificationBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }
}