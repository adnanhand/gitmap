package com.a.hub.ui.auth

import androidx.lifecycle.viewModelScope
import com.a.hub.core.auth.Access
import com.a.hub.core.auth.SimpleAuthManager
import com.a.hub.data.Constants
import com.a.hub.data.repository.ApiRepository
import com.a.hub.ui.base.BaseViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel      //with Flow
class AuthViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val authManager: SimpleAuthManager
): BaseViewModel(null) {

    private val _token = MutableStateFlow<Access?>(null)
    private val _userState = MutableStateFlow(false)

    val userState: StateFlow<Boolean> = _userState
    val token: StateFlow<Access?> = _token

    private val _out = Channel<Unit>(capacity = Channel.CONFLATED)
    val out = _out.receiveAsFlow()

    init {
        _userState.value = authManager.hasActiveSession()
    }

    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String,
    ){
        viewModelScope.launch {
            when (val data = apiRepository.getAccessToken(Constants.Auth.URL, clientId, clientSecret, code)) {
                is NetworkResponse.Success -> {
                    authManager.setAccess(data.body)
                    _token.value = data.body
                    _userState.value = authManager.hasActiveSession()
                }
                is NetworkResponse.ServerError -> message.value = data.error?.message
                is NetworkResponse.NetworkError -> message.value = data.error.message
                is NetworkResponse.UnknownError -> message.value = data.error.message
            }
        }
    }

    fun signOut() {
        authManager.clearAccess()
        _userState.value = false
        _token.value = null
        _out.trySend(Unit)
    }

}