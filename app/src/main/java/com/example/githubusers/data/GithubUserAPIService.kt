package com.example.githubusers.data

import com.example.githubusers.model.SearchUserModelResponse
import com.example.githubusers.model.SearchUserModelView
import com.example.githubusers.model.UserModelView
import com.example.githubusers.presentation.GithubUserDataInterface
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubUserAPIService : GithubUserDataInterface {

    private val observers: MutableSet<Observer<SearchUserModelView>> = mutableSetOf()
    private var baseURL: String = "https://api.github.com"
    private val compositeDisposable = CompositeDisposable()

    override fun registerObserver(observer: Observer<SearchUserModelView>) {
        observers.add(observer)
    }

    override fun unregisterObserver(observer: Observer<SearchUserModelView>) {
        observers.remove(observer)
    }

    override fun clearDisposable() {
        compositeDisposable.clear()
    }

    override fun getDataSearch(query: String) {
        val requestAPIInterface = Retrofit.Builder()
            .baseUrl(baseURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubUserAPIInterface::class.java)
        val disposable = requestAPIInterface.getUsers(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<SearchUserModelResponse>() {
                override fun onComplete() {
                    compositeDisposable.remove(this)
                }

                override fun onNext(t: SearchUserModelResponse) {
                    notifyObserver(t)
                }

                override fun onError(e: Throwable) {
                    notifyObserverError(e)
                    compositeDisposable.remove(this)
                }
            })
        compositeDisposable.add(disposable)
    }

    private fun notifyObserverError(throwable: Throwable) {
        if (!observers.isNullOrEmpty()) {
            for (observer in observers) {
                observer.onError(throwable)
            }
        }
    }

    private fun notifyObserver(searchUserModelResponse: SearchUserModelResponse) {
        if (!observers.isNullOrEmpty()) {
            for (observer in observers) {
                observer.onNext(convertResponseToModel(searchUserModelResponse))
            }
        }
    }

    private fun convertResponseToModel(searchUserModelResponse: SearchUserModelResponse): SearchUserModelView {
        val resultList = mutableListOf<UserModelView>()
        searchUserModelResponse.listUser.forEach {
            resultList.add(UserModelView().apply {
                this.avatar = it.avatar.orEmpty()
                this.username = it.username.orEmpty()
            })
        }
        return SearchUserModelView().apply {
            this.totalCount = searchUserModelResponse.totalCount
            this.listUserModelView = resultList
        }
    }

}
