package com.example.modelresp


import com.google.gson.annotations.SerializedName

data class UserResp(
    @SerializedName("instagram_username")
    val instagramUsername: String,
    @SerializedName("is_private")
    val isPrivate: Boolean,
    @SerializedName("is_verified")
    val isVerified: Boolean,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("total_posts")
    val totalPosts: Int,
    @SerializedName("user_description")
    val userDescription: String,
    @SerializedName("user_followers")
    val userFollowers: Int,
    @SerializedName("user_following")
    val userFollowing: Int,
    @SerializedName("user_fullname")
    val userFullname: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_link")
    val userLink: String,
    @SerializedName("user_profile_pic")
    val userProfilePic: String
)