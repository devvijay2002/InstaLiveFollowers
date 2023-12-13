package com.example.userdata.viewModel

import androidx.lifecycle.MutableLiveData

import com.example.modelresp.UserResp
import com.example.userdata.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

  class M1ViewModel : BaseViewModel() {
    lateinit var disposable: Disposable
    var error = MutableLiveData<Throwable>()
    var progress = MutableLiveData<Boolean>()
    val UserResp = MutableLiveData<UserResp>()




      fun hitUserData(url: String, referer: String, secFetchSite: String) {
          disposable = api.getUserData(url, referer, secFetchSite)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .doOnSubscribe {
                  progress.value = true
              }
              .doOnTerminate {
                  progress.value = false
              }
              .subscribe({
                  UserResp.value = it
              }, {
                  error.value = it
              })
      }

      }











