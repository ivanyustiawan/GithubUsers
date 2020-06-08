package com.example.githubusers.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserModelResponse(
    @SerializedName("avatar_url") var avatar: String? = "",
    @SerializedName("login") var username: String? = ""
) : Serializable