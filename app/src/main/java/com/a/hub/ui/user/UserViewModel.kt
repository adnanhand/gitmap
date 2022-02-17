package com.a.hub.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.hub.core.livedata.Resource
import com.a.hub.data.model.Repo
import com.a.hub.data.model.User
import com.a.hub.data.repository.ApiRepository
import com.a.hub.ui.base.BaseViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val apiRepository: ApiRepository
): BaseViewModel(null) {

    private val _user = MutableLiveData<User?>()
    private val _repositories = MutableLiveData<Resource<List<Repo>>>()

    val user: LiveData<User?> = _user
    val repositories: LiveData<Resource<List<Repo>>> = _repositories

    fun getUser(url: String){
        viewModelScope.launch {
            when (val data = apiRepository.getUser(url)) {
                is NetworkResponse.Success -> {
                    _user.value = data.body
                    getUserRepositories(data.body.reposUrl)
                }
                is NetworkResponse.ServerError -> message.value = data.error?.message
                is NetworkResponse.NetworkError -> message.value = data.error.message
                is NetworkResponse.UnknownError -> message.value = data.error.message
            }

        }
    }

    private fun getUserRepositories(url: String){
        if(_repositories.value?.data?.size ?: 0 > 0) {
            message.value = "Repositories already loaded"
            return
        }
        viewModelScope.launch {
            when (val data = apiRepository.getUserRepositories(url)) {
                is NetworkResponse.Success -> {
                    _repositories.value = Resource.success(data.body)
                }
                is NetworkResponse.ServerError -> message.value = data.error?.message
                is NetworkResponse.NetworkError -> message.value = data.error.message
                is NetworkResponse.UnknownError -> message.value = data.error.message
            }
        }
    }


}