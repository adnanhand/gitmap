package com.a.hub.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class NetworkStatusFlow(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @ExperimentalCoroutinesApi
    val networkStatus = callbackFlow {
        val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                trySend(NetworkStatus.Inactive)
            }
            override fun onAvailable(network: Network) {
                trySend(NetworkStatus.Active)
            }
            override fun onLost(network: Network) {
                trySend(NetworkStatus.Inactive)
            }
        }
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkStatusCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkStatusCallback)
        }
    }.distinctUntilChanged()
}

sealed class NetworkStatus {
    object Active : NetworkStatus()
    object Inactive : NetworkStatus()
}

@FlowPreview
inline fun <Result> Flow<NetworkStatus>.mapper(
    crossinline onActive: suspend () -> Result,
    crossinline onInactive: suspend () -> Result,
): Flow<Result> = map { status ->
    when (status) {
        NetworkStatus.Active -> onActive()
        NetworkStatus.Inactive -> onInactive()
    }
}
