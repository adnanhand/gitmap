package com.a.hub.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.hub.core.livedata.Resource
import com.a.hub.data.model.Issue
import com.a.hub.data.model.Repo
import com.a.hub.data.model.User
import com.a.hub.data.repository.ApiRepository
import com.a.hub.ui.base.BaseViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val apiRepository: ApiRepository
): BaseViewModel(null) {

    private val _repo = MutableLiveData<Resource<Repo>>()
    private val _issues = MutableLiveData<Resource<List<Issue>>>()
    private val _contributors = MutableLiveData<Resource<List<User>>>()
    private val _markdown = MutableLiveData<String>()

    val repo: LiveData<Resource<Repo>> = _repo
    val issues: LiveData<Resource<List<Issue>>> = _issues
    val contributors: LiveData<Resource<List<User>>> = _contributors
    val markdown: LiveData<String> = _markdown

    //fun getRepository(user: String, repo: String){
    fun getRepository(url: String){
        viewModelScope.launch {
            _repo.value = Resource.loading(null)
            when (val data = apiRepository.getRepository(url)) {
                is NetworkResponse.Success -> {
                    _repo.value = Resource.success(data.body)
                    val s = "https://raw.githubusercontent.com/${data.body.fullName}/${data.body.defaultBranch}/README.md"
                    _markdown.value = s
                }
                is NetworkResponse.ServerError -> message.value = data.error?.message
                is NetworkResponse.NetworkError -> message.value = data.error.message
                is NetworkResponse.UnknownError -> message.value = data.error.message
            }
        }
    }

    fun getIssues(url: String){
        if(_issues.value?.data?.size ?: 0 > 0) {
            message.value = "Issue already loaded"
            return
        }
        viewModelScope.launch {
            _issues.value = Resource.loading(null)
            when (val data = apiRepository.getIssues(url)) {
                is NetworkResponse.Success -> {
                    _issues.value = Resource.success(data.body)
                }
                is NetworkResponse.ServerError -> message.value = data.error?.message
                is NetworkResponse.NetworkError -> message.value = data.error.message
                is NetworkResponse.UnknownError -> message.value = data.error.message
            }

        }
    }

    fun getContributors(url: String){
        if(_contributors.value?.data?.size ?: 0 > 0) {
            message.value = "Contributors already loaded"
            return
        }
        viewModelScope.launch {
            _contributors.value = Resource.loading(null)
            when (val data = apiRepository.getContributors(url)) {
                is NetworkResponse.Success -> {
                    _contributors.value = Resource.success(data.body)
                }
                is NetworkResponse.ServerError -> message.value = data.error?.message
                is NetworkResponse.NetworkError -> message.value = data.error.message
                is NetworkResponse.UnknownError -> message.value = data.error.message
            }
        }
    }


}