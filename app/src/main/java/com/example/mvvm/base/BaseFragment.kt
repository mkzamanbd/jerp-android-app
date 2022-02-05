package com.example.mvvm.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.interfaces.InitialComponent
import java.lang.IllegalArgumentException
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
) : Fragment(), InitialComponent {

    @Inject
    lateinit var spManager: SharedPreferenceManager
    protected lateinit var mContext: Context
    protected lateinit var mActivity: Activity
    protected lateinit var baseActivity: BaseActivity

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