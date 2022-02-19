package me.kzaman.android.ui.view.fragments

import android.os.Bundle
import android.view.View
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.databinding.FragmentUserDetailBinding
import me.kzaman.android.utils.toastSuccess
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
            toastSuccess(mActivity, productId.toString())
        }
    }
}