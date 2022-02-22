package me.kzaman.android.ui.view.fragments

import android.os.Bundle
import me.kzaman.android.R
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.databinding.FragmentNotificationBinding

class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {

    override val layoutId: Int = R.layout.fragment_notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }
}