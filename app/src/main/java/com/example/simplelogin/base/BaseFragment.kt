package com.example.simplelogin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.simplelogin.data.UserPreferences
import com.example.simplelogin.network.RetrofitClient
import com.example.simplelogin.network.UserApi
import com.example.simplelogin.ui.view.auth.AuthActivity
import com.example.simplelogin.utils.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
) : Fragment() {

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
}