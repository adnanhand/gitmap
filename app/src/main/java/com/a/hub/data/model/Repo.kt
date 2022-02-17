package com.a.hub.data.model

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("full_name")
    val fullName: String,
    val description: String,
    val visibility: String,
    val url: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    val private: Boolean,
    val owner: User,
    val fork: Boolean,
    val size: Int,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    val language: String,
    @SerializedName("has_issues")
    val hasIssues: Boolean,
    @SerializedName("has_projects")
    val hasProjects: Boolean,
    @SerializedName("has_downloads")
    val hasDownloads: Boolean,
    @SerializedName("has_wiki")
    val hasWiki: Boolean,
    @SerializedName("has_pages")
    val hasPages: Boolean,
    @SerializedName("forks_count")
    val forksCount: Int,
    val archived: Boolean,
    val disabled: Boolean,
    //val license: String,
    val topics: List<String>,
    val forks: Int,
    @SerializedName("open_issues")
    val openIssues: Int,
    val watchers: Int,
    @SerializedName("default_branch")
    val defaultBranch: String,
    @SerializedName("network_count")
    val networkCount: String,
    @SerializedName("subscribers_count")
    val subscribersCount: String,
    @SerializedName("issues_url")
    val issuesUrl: String,
    @SerializedName("contributors_url")
    val contributorsUrl: String,
): Model(){
    override fun toString(): String {
        if(fullName.isEmpty()) return name
        return fullName
    }
}