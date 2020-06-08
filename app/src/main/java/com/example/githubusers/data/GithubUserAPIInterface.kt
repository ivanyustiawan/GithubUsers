package com.example.githubusers.data

import com.example.githubusers.model.SearchUserModelResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubUserAPIInterface {

    @GET("/search/users")
    fun getUsers(@Query("q") query: String): Observable<SearchUserModelResponse>

}