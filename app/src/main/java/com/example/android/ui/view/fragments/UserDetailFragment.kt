package com.example.android.ui.view.fragments

import android.os.Bundle
import android.view.View
import com.example.android.base.BaseFragment
import com.example.android.databinding.FragmentUserDetailBinding
import com.example.android.utils.toastSuccess
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : BaseFragment<FragmentUserDetailBinding>(
    FragmentUserDetailBinding::inflate
) {
    private var productId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productId = arguments?.getString("productId")

        if (productId != null) {
            mActivity.toastSuccess(productId.toString())
        }
    }
}