package com.a.hub.data.repository

import com.a.hub.data.api.ApiService
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun searchRepositories(
        query: String,
        sort: String,
        order: String,
        page: Int
    ) = api.searchRepository(query, sort, order, page)

    suspend fun getRepository(user: String, repo: String) = api.getRepository(user, repo)
    suspend fun getRepository(url: String) = api.getRepository(url)
    suspend fun getIssues(url: String) = api.getIssues(url)
    suspend fun getContributors(url: String) = api.getContributors(url)
    suspend fun getUser(url: String) = api.getUser(url)
    suspend fun getUserRepositories(url: String) = api.getUserRepositories(url)

    //OAUTH
    suspend fun getAuthUser(token: String) = api.getAuthUser(token)
    suspend fun getAccessToken(
        url: String,
        clientId: String,
        clientSecret: String,
        code: String,
    ) = api.getAccessToken(url, clientId, clientSecret, code)

}