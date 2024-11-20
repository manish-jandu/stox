package com.example.stox.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stox.data.NetworkUtils.Resource
import com.example.stox.data.model.Holding
import com.example.stox.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _userHoldingsState = MutableStateFlow<Resource<List<Holding>>>(Resource.Loading())
    val userHoldingsState: StateFlow<Resource<List<Holding>>> get() = _userHoldingsState.asStateFlow()

    init {
        fetchUserHoldings()
    }

    fun fetchUserHoldings() {
        viewModelScope.launch {
            try {
                _userHoldingsState.value = Resource.Loading()
                val result = repository.getUserHoldings()
                _userHoldingsState.value = result
            } catch (e: Exception) {
                _userHoldingsState.value = Resource.Error("An error occurred: ${e.localizedMessage}")
            }
        }
    }
}
