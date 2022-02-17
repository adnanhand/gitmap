package com.a.hub.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.a.hub.helper.NetworkStatusFlow
import com.a.hub.helper.mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

abstract class BaseViewModel(
    networkStatusFlow: NetworkStatusFlow?
): ViewModel() {

    var message = MutableLiveData<String>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    val network = networkStatusFlow?.networkStatus?.mapper(
        onActive = { true },
        onInactive = { false }
    )?.asLiveData(Dispatchers.IO)

}