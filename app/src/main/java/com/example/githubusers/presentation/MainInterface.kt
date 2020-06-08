package com.example.githubusers.presentation

import com.example.githubusers.model.UserModelView

interface MainInterface {

    fun onDataSet(list: List<UserModelView>)

    fun onEmptyResult()

    fun onError(error: String)

    fun onEmptyQuery()
}