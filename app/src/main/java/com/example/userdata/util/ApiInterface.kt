package com.example.userdata.util



import com.example.modelresp.LoginResp
import com.example.modelresp.PostResp
import com.example.modelresp.SignUpResp
import com.example.modelresp.UpdatedUserResp
import com.example.modelresp.UserResp
import io.reactivex.Observable
import retrofit2.http.Body


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @GET
    fun getUserData(
        @Url url: String,
        @Header("Referer") referer: String,
        @Header("Sec-Fetch-Site") secFetchSite: String
    ): Observable<UserResp>

    @GET
    fun getAllAssistantData(
        @Url url: String
    ) :Observable<UpdatedUserResp>

    @POST("api/Assistant")
    fun PostUserData(
        @Query ("userId") userId:String,
        @Query ("followerCount") followerCount:Int,


    ) : Observable<PostResp>

    @POST("api/User")
    fun PostSignUPData(@Body requestBody: Map<String, String>): Observable<SignUpResp>

    @GET("api/Login")
    fun getLoginData(
        @Query ("username") username:String,
        @Query ("password") password:String,
    ): Observable<LoginResp>

}



