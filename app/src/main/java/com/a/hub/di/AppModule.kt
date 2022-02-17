package com.a.hub.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.a.hub.core.AppData
import com.a.hub.core.auth.SimpleAuthManager
import com.a.hub.data.api.ApiClient
import com.a.hub.data.api.ApiService
import com.a.hub.data.repository.ApiRepository
import com.a.hub.helper.NetworkStatusFlow
import com.a.hub.helper.SimpleSecure
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {


    @Singleton
    @Provides
    fun provideApiService(): ApiService = ApiClient().service


    @Singleton
    @Provides
    fun provideApiRepository(apiService: ApiService): ApiRepository = ApiRepository(apiService)


    @Singleton
    @Provides
    fun provideSimpleAuthManager(
        @ApplicationContext context: Context,
        simpleSecure: SimpleSecure
    ): SimpleAuthManager = SimpleAuthManager(context, simpleSecure)


    @Singleton
    @Provides
    fun provideAppData(
        dataStore: DataStore<Preferences>,
        simpleSecure: SimpleSecure
    ): AppData = AppData(dataStore, simpleSecure)

    @Singleton
    @Provides
    fun provideNetworkStatusFlow(
        @ApplicationContext context: Context
    ): NetworkStatusFlow = NetworkStatusFlow(context)

    @Singleton
    @Provides
    fun provideSecure(): SimpleSecure = SimpleSecure()

    @Singleton
    @Provides
    fun dataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        preferencesDataStore(name = "data-store").getValue(context, String::javaClass)





}