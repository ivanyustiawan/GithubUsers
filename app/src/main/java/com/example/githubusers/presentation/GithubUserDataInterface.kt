package com.example.githubusers.presentation

import com.example.githubusers.model.SearchUserModelView
import io.reactivex.Observer

interface GithubUserDataInterface {

    fun registerObserver(observer: Observer<SearchUserModelView>)

    fun unregisterObserver(observer: Observer<SearchUserModelView>)

    fun clearDisposable()

    fun getDataSearch(query: String)
}