package com.a.hub.data.model

class Issue(
    val title: String,
    val body: String,
    val number: Int,
    val user: User,
    val state: String,
    val comments: Int,
    val url: String,
    val labels: List<Label>,
): Model()