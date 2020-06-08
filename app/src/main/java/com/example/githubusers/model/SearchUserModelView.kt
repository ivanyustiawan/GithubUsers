package com.example.githubusers.model

import java.io.Serializable

class SearchUserModelView : Serializable {
    var totalCount: Int = 0
    var listUserModelView: List<UserModelView> = listOf()
}