package com.example.modelresp


import com.google.gson.annotations.SerializedName

data class LoginResp(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("responseCode")
    val responseCode: Int,
    @SerializedName("result")
    val result: Result
) {
    data class Result(
        @SerializedName("age")
        val age: Int,
        @SerializedName("email")
        val email: String,
        @SerializedName("firstName")
        val firstName: String,
        @SerializedName("lastName")
        val lastName: String,
        @SerializedName("number")
        val number: Int
    )
}