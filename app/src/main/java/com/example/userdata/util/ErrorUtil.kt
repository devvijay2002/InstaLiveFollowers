package com.example.userdata.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.userdata.R
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorUtil {
    val TAG = ErrorUtil::class.simpleName

    fun handlerGeneralError(context: Context?, throwable: Throwable) {
        Log.e(TAG, "Error: ${throwable.message}")
        throwable.printStackTrace()

        if (context == null) return

        when (throwable) {
            //For Display Toast

            is ConnectException -> Toast.makeText(
                    context,
                    "Network Error PLease Try Later ",
                    Toast.LENGTH_SHORT
            ).show()
            is SocketTimeoutException -> Toast.makeText(
                    context,
                    "Connection Lost PLease Try Later",
                    Toast.LENGTH_SHORT
            ).show()
            is UnknownHostException, is InternalError -> Toast.makeText(
                    context,
                    "Server Error PLease Try Later",
                    Toast.LENGTH_SHORT
            ).show()

            is HttpException -> {
                try {
                    when (throwable.code()) {
                        401 -> {
                            //Logout
                            displayError(context,throwable)
                        }
                        else -> {
                            displayError(context, throwable)
                        }
                    }
                } catch (exception: Exception) {
                    Log.e("error", exception.toString())
                }
            }
            else -> {
                Toast.makeText(
                        context, context.resources.getString(R.string.something_went_wrong),
                        Toast.LENGTH_LONG
                ).show()
            }

        }
    }


    fun displayError(context: Context, exception: HttpException) {
        Log.i(TAG, "displayError()")
        try {
            val errorBody = Gson().fromJson(
                    exception.response()!!.errorBody()?.charStream(),
                    ErrorBean::class.java
            )
            Toast.makeText(context, errorBody.message, Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Log.e("MyExceptions", e.message!!)
            Toast.makeText(
                    context, context.getString(R.string.error_exception),
                    Toast.LENGTH_SHORT
            ).show()
        }
    }
}

