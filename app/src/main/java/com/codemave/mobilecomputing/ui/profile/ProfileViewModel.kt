package com.codemave.mobilecomputing.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codemave.mobilecomputing.Graph
import com.codemave.mobilecomputing.data.entity.Category
import com.codemave.mobilecomputing.data.entity.LoginInfo
import com.codemave.mobilecomputing.data.repository.LoginInfoRepository
import com.codemave.mobilecomputing.ui.home.HomeViewState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loginInfoRepository: LoginInfoRepository = Graph.loginInfoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileViewState(logininfos = loginInfoRepository.loginInfos()))
    val _selectedLoginInfo = MutableStateFlow<LoginInfo?>(null)

    val state: StateFlow<ProfileViewState>
        get() = _state

    fun LoginInfoSelected(): LoginInfo? {
        return _selectedLoginInfo.value
    }
    fun onLoginInfoSelected(loginInfo:LoginInfo) {
        _selectedLoginInfo.value= loginInfo
    }



}


data class ProfileViewState(
    val logininfos: Flow<List<LoginInfo>>,// = emptyList(),
    val selectedLoginInfo: LoginInfo? = null
)