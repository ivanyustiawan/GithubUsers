package com.example.githubusers.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchUserModelResponse(
    @SerializedName("total_count") var totalCount: Int,
    @SerializedName("items") var listUser: List<UserModelResponse>
) : Serializable