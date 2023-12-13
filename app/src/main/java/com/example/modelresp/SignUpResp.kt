package com.example.modelresp


import com.google.gson.annotations.SerializedName

data class SignUpResp(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("responseCode")
    val responseCode: Int,
    @SerializedName("result")
    val result: Int
)