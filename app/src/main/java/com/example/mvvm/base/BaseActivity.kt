package com.example.mvvm.base

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.interfaces.InitialComponent
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), InitialComponent {
    @Inject
    lateinit var spManager: SharedPreferenceManager
    abstract override fun init()

    abstract override fun setToolbarTitle(title: String)

    /**
     * ...set toolbar title
     * ...modify title and textView if needed
     * @param title toolbar title
     * @param tvTitle textView object
     */
    fun setToolbarTitle(title: String, tvTitle: TextView) {
        tvTitle.text = title
    }
}