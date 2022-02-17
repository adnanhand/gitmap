package com.a.hub.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.a.hub.core.AppData
import com.a.hub.core.auth.SimpleAuthManager
import com.a.hub.core.livedata.Resource
import com.a.hub.core.livedata.Status
import com.a.hub.data.model.Recent
import com.a.hub.data.model.Repo
import com.a.hub.data.model.User
import com.a.hub.data.repository.ApiRepository
import com.a.hub.helper.NetworkStatusFlow
import com.a.hub.ui.base.BaseViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val appData: AppData,
    authManager: SimpleAuthManager,
    networkStatusFlow: NetworkStatusFlow
): BaseViewModel(networkStatusFlow) {

    private val _repositories = MutableLiveData<Resource<List<Repo>>>()
    private val _user = MutableLiveData<User?>()

    private var activeSearch = SearchState()
    private var activeSort = SortState()

    val sortState = MutableLiveData(activeSort)
    val userState = MutableLiveData<Boolean>()

    val user: LiveData<User?> = _user
    val repositories: LiveData<Resource<List<Repo>>> = _repositories

    val search = MutableLiveData(false)
    val searchMode = MutableLiveData(false)

    val recent = appData.getData("recent").map {
        Gson().fromJson<MutableList<Recent>>(it, object : TypeToken<MutableList<Recent>>() {}.type)
    }.catch {
        mutableListOf<Recent>()
    }.asLiveData()


    init {
        val hasActiveSession = authManager.hasActiveSession()
        userState.value = hasActiveSession
        if(hasActiveSession){
            val t = authManager.getAccessToken()
            if(t != null) loadUser("token $t")
        }
        _repositories.value = Resource.success(listOf())

    }



    //TODO check error response for 401, 403
    private fun loadUser(token: String){
        viewModelScope.launch {
            when (val data = apiRepository.getAuthUser(token)) {
                is NetworkResponse.Success -> {
                    _user.value = data.body
                }
                is NetworkResponse.ServerError -> message.value = data.error?.message
                is NetworkResponse.NetworkError -> message.value = data.error.message
                is NetworkResponse.UnknownError -> message.value = data.error.message
            }
        }
    }

    //TODO check error response for 403
    private fun searchRepositories(){
        viewModelScope.launch {
            _repositories.value =
                Resource.loading(if(activeSearch.page == 1) listOf() else null)
            queryToRecent(activeSearch.query)
            val data = apiRepository.searchRepositories(
                activeSearch.query,
                activeSort.sort,
                activeSort.order,
                activeSearch.page
            )

            when (data) {
                is NetworkResponse.Success -> {
                    _repositories.value = Resource.success(data.body.items)
                }
                is NetworkResponse.ServerError -> message.value = data.error?.message
                is NetworkResponse.NetworkError -> message.value = data.error.message
                is NetworkResponse.UnknownError -> message.value = data.error.message
            }


        }
    }

    fun onStateChanged(stateChanged: StateChanged){
        when(stateChanged){
            is StateChanged.OrderChanged -> {
                if(repositories.value?.status == Status.LOADING) return
                updateSortState(activeSort.copy(order = stateChanged.order))
                updateSearchState(activeSearch.copy(page = 1))
            }
            is StateChanged.SortChanged -> {
                if(repositories.value?.status == Status.LOADING) return
                updateSortState(activeSort.copy(sort = stateChanged.sort))
                updateSearchState(activeSearch.copy(page = 1))
            }
            is StateChanged.QueryChanged -> {
                if(!activeSearch.query.equals(stateChanged.query)) {
                    updateSearchState(activeSearch.copy(query = stateChanged.query, page = 1))
                }
            }
            is StateChanged.PageChanged ->
                updateSearchState(activeSearch.copy(page = activeSearch.page + stateChanged.page))
        }
    }

    private fun updateSortState(state: SortState) {
        activeSort = state
        sortState.value = activeSort
    }

    private fun updateSearchState(state: SearchState) {
        activeSearch = state
        searchRepositories()
    }

    /*private fun initRecent() {
        viewModelScope.launch {
            appData.getData("recent").map {}.catch {}.collect { td }
        }
    }*/

    private fun queryToRecent(query: String) {
        if(query.isEmpty()) return
        var list: MutableList<Recent> //TODO carefully - every search from init will reset data
        if(recent.value is MutableList<Recent>){
            list = recent.value as MutableList<Recent>
            list = list.filterNot { it.text == query }.toMutableList()
            list.add(0, Recent(System.currentTimeMillis(), query))
        }else{
            list = mutableListOf(Recent(System.currentTimeMillis(), query))
        }
        viewModelScope.launch(Dispatchers.Unconfined){
            appData.setData("recent", Gson().toJson(list))
        }
    }

    fun deleteRecent(item: Recent) {
        if(recent.value is MutableList<Recent>){
            val list = recent.value as MutableList<Recent>
            if(list.remove(item)) {
                viewModelScope.launch(Dispatchers.Unconfined) {
                    appData.setData("recent", Gson().toJson(list))
                }
            }
        }
    }

    fun switchSearch(b: Boolean) {
        search.value = b
    }

    fun switchMode(b: Boolean) {
        searchMode.value = b
    }


}