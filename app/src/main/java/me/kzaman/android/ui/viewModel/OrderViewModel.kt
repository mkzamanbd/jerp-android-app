package me.kzaman.android.ui.viewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import me.kzaman.android.base.BaseViewModel
import me.kzaman.android.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    val repository: ProductRepository,
) : BaseViewModel(repository) {

}