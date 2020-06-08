package com.example.githubusers.presentation

import com.example.githubusers.data.GithubUserAPIService
import com.example.githubusers.model.SearchUserModelView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class MainPresenter(private var mainInterface: MainInterface?) {

    private val githubUserDataInterface = GithubUserAPIService()

    fun registerObserver() {
        githubUserDataInterface.registerObserver(getObserver())
    }

    fun unregisterObserver() {
        githubUserDataInterface.unregisterObserver(getObserver())
    }

    private fun getObserver(): Observer<SearchUserModelView> {
        return object : Observer<SearchUserModelView> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: SearchUserModelView) {
                mainInterface?.onDataSet(t.listUserModelView)
                if (t.listUserModelView.isNullOrEmpty()) {
                    mainInterface?.onEmptyResult()
                }
            }

            override fun onError(e: Throwable) {
                val response = e as com.jakewharton.retrofit2.adapter.rxjava2.HttpException
                mainInterface?.onError(response.message())
            }

        }
    }

    fun getData(query: String) {
        if (query.isNullOrEmpty()) {
            mainInterface?.onEmptyQuery()
        } else {
            githubUserDataInterface.getDataSearch(query)
        }
    }

    fun releaseReference() {
        mainInterface = null
        githubUserDataInterface.clearDisposable()
    }
}