package com.a.hub.data.api

import com.a.hub.core.auth.Access
import com.a.hub.data.api.response.ErrorResponse
import com.a.hub.data.api.response.SearchResponse
import com.a.hub.data.model.Issue
import com.a.hub.data.model.Repo
import com.a.hub.data.model.User
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.*

interface ApiService {

    @Headers("Accept: application/vnd.github.v3+json")
    @FormUrlEncoded
    @POST
    suspend fun getAccessToken(
        @Url url: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): NetworkResponse<Access, ErrorResponse>

    @GET("user")
    suspend fun getAuthUser(
        @Header("Authorization") token: String
    ): NetworkResponse<User, ErrorResponse>

    @GET("search/repositories")
    suspend fun searchRepository(
        @Query("q", encoded = true) query: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Int,
    ): NetworkResponse<SearchResponse<Repo>, ErrorResponse>

    @GET
    suspend fun getUserRepositories(
        @Url url: String
    ): NetworkResponse<List<Repo>, ErrorResponse>

    @GET("repos/{user}/{repo}")
    suspend fun getRepository(
        @Path("user") user: String,
        @Path("repo") repo: String
    ): NetworkResponse<Repo, ErrorResponse>

    @GET("users/{name}")
    suspend fun getUserByName(
        @Path("name") name: String
    ): NetworkResponse<SearchResponse<User>, ErrorResponse>

    @GET
    suspend fun getRepository(@Url url: String): NetworkResponse<Repo, ErrorResponse>
    @GET
    suspend fun getUser(@Url url: String): NetworkResponse<User, ErrorResponse>
    @GET
    suspend fun getIssues(@Url url: String): NetworkResponse<List<Issue>, ErrorResponse>
    @GET
    suspend fun getContributors(@Url url: String): NetworkResponse<List<User>, ErrorResponse>

}