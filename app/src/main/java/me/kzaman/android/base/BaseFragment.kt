package me.kzaman.android.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import me.kzaman.android.interfaces.InitialComponent
import me.kzaman.android.utils.LoadingUtils

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
) : Fragment(), InitialComponent {

    protected lateinit var mContext: Context
    protected lateinit var mActivity: Activity
    protected lateinit var baseActivity: BaseActivity
    protected lateinit var loadingUtils: LoadingUtils

    private var _binding: VB? = null

    val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if (_binding == null)
            throw IllegalArgumentException("Binding cannot be null")
        return binding.root
    }

    override fun init() {}
    override fun setToolbarTitle(title: String) {}
}