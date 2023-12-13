package com.example.userdata.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.modelresp.LoginResp
import com.example.modelresp.UpdatedUserResp
import com.example.modelresp.PostResp
import com.example.modelresp.SignUpResp

import com.example.userdata.base.BaseViewModel2
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

  class M1ViewModel2 : BaseViewModel2() {
    lateinit var disposable: Disposable
    var error = MutableLiveData<Throwable>()
    var progress = MutableLiveData<Boolean>()
    val GetUserUpdatedData = MutableLiveData<UpdatedUserResp>()
    val PostResp = MutableLiveData<PostResp>()
    val SignUpResp = MutableLiveData<SignUpResp>()
    val LoginResp = MutableLiveData<LoginResp>()




      fun hitUpdatedUserData(url: String) {
          disposable = api.getAllAssistantData(url)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .doOnSubscribe {
                  progress.value = true
              }
              .doOnTerminate {
                  progress.value = false
              }
              .subscribe({
                  GetUserUpdatedData.value = it
              }, {
                  error.value = it
              })
      }
      fun hitPostdUserData(userId:String,followerCount:Int) {
          disposable = api.PostUserData(userId,followerCount)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .doOnSubscribe {
                  progress.value = true
              }
              .doOnTerminate {
                  progress.value = false
              }
              .subscribe({
                  PostResp.value = it
              }, {
                  error.value = it
              })
      }

      fun hitLogin(username:String,password:String) {
          disposable = api.getLoginData(username,password)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .doOnSubscribe {
                  progress.value = true
              }
              .doOnTerminate {
                  progress.value = false
              }
              .subscribe({
                  LoginResp.value = it
              }, {
                  error.value = it
              })
      }

      fun hitSignUp(
          username: String,
          password: String,
          firstName: String,
          lastName: String,
          email: String,
          age: Int,
          number: Int
      ) {
          val signUpData: Map<String, Any> = mapOf(
              "username" to username,
              "password" to password,
              "firstName" to firstName,
              "lastName" to lastName,
              "email" to email,
              "age" to age,
              "number" to number
          )


          disposable = api.PostSignUPData(signUpData as Map<String, String>)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .doOnSubscribe {
                  progress.value = true
              }
              .doOnTerminate {
                  progress.value = false
              }
              .subscribe({
                  SignUpResp.value = it
              }, {
                  error.value = it
              })
      }



  }











