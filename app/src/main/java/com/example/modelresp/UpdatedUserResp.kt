package com.example.modelresp


import com.google.gson.annotations.SerializedName

data class UpdatedUserResp(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("responseCode")
    val responseCode: Int,
    @SerializedName("result")
    val result: List<Result>
) {
    data class Result(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("followerCount")
        val followerCount: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("progress")
        val progress: Int,
        @SerializedName("userId")
        val userId: String
    )
}