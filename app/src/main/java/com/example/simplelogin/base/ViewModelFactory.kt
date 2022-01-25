package com.example.simplelogin.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simplelogin.repository.AuthRepository
import com.example.simplelogin.repository.ProfileRepository
import com.example.simplelogin.ui.view.auth.AuthViewModel
import com.example.simplelogin.ui.viewModel.ProfileViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: BaseRepository,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository as ProfileRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}