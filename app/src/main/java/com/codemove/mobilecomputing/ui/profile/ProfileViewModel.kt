package com.codemave.mobilecomputing.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codemave.mobilecomputing.Graph
import com.codemave.mobilecomputing.data.entity.Category
import com.codemave.mobilecomputing.data.entity.LoginInfo
import com.codemave.mobilecomputing.data.repository.LoginInfoRepository
import com.codemave.mobilecomputing.ui.home.HomeViewState
import com.codemave.mobilecomputing.ui.task.TaskViewState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(

) : ViewModel() {
    private val _selectedLogin = MutableStateFlow(LoginInfo("bgmrsln","123456"))
    private val _state = MutableStateFlow(ProfileViewState(selectedLoginInfo = _selectedLogin.value))


    val state: StateFlow<ProfileViewState>
        get() = _state

    fun onLoginSelected(login: LoginInfo) {
        _selectedLogin.value = login
    }

}
data class ProfileViewState(
    val selectedLoginInfo: LoginInfo
)