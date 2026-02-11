package com.example.mobile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class UiState(
    val token: String? = null,
    val user: UserResponse? = null,
    val loading: Boolean = false,
    val error: String? = null
)

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val tokenStore = TokenStore(app.applicationContext)

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    private var cachedToken: String? = null

    private val api = ApiClient.create { cachedToken }
    private val repo = AuthRepository(api, tokenStore)

    init {
        viewModelScope.launch {
            cachedToken = tokenStore.tokenFlow.first()
            _state.value = _state.value.copy(token = cachedToken)
            if (!cachedToken.isNullOrBlank()) loadMe()
        }
    }

    fun register(email: String, username: String, password: String) {
        viewModelScope.launch {
            runCatching {
                _state.value = _state.value.copy(loading = true, error = null)
                repo.register(email, username, password)
            }.onFailure {
                _state.value = _state.value.copy(error = it.message)
            }
            _state.value = _state.value.copy(loading = false)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            runCatching {
                _state.value = _state.value.copy(loading = true, error = null)
                repo.login(email, password)
                cachedToken = tokenStore.tokenFlow.first()
                _state.value = _state.value.copy(token = cachedToken)
                loadMe()
            }.onFailure {
                _state.value = _state.value.copy(error = it.message)
            }
            _state.value = _state.value.copy(loading = false)
        }
    }

    fun loadMe() {
        viewModelScope.launch {
            runCatching {
                _state.value = _state.value.copy(loading = true, error = null)
                val me = repo.me()
                _state.value = _state.value.copy(user = me)
            }.onFailure {
                _state.value = _state.value.copy(error = it.message)
            }
            _state.value = _state.value.copy(loading = false)
        }
    }

    fun updateUsername(newUsername: String) {
        viewModelScope.launch {
            runCatching {
                _state.value = _state.value.copy(loading = true, error = null)
                val updated = repo.updateUsername(newUsername)
                _state.value = _state.value.copy(user = updated)
            }.onFailure {
                _state.value = _state.value.copy(error = it.message)
            }
            _state.value = _state.value.copy(loading = false)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repo.logout()
            cachedToken = null
            _state.value = UiState(token = null, user = null)
        }
    }
}
