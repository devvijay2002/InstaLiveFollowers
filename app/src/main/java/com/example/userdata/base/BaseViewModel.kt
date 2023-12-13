package com.example.userdata.base


import androidx.lifecycle.ViewModel
import com.example.userdata.util.ApiInterface
import com.example.userdata.util.Retrofit
import com.example.userdata.util.Retrofit2


abstract class BaseViewModel : ViewModel() {

    val api : ApiInterface by lazy {
        Retrofit.createBaseApiService()

    }

}