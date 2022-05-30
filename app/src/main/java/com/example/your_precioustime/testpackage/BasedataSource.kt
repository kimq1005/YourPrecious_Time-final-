package com.example.your_precioustime.testpackage

import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import retrofit2.Response

abstract class BasedataSource {
    private val gson = Gson()

    protected suspend fun <T> getResult(call: suspend ()-> Response<T>) : RemoteResult<T>{

        try{
            val response = call()

            if(response.isSuccessful){
                if(response.code() != 201){
                    val body = response.body()
                    if(body !=null){
                        return RemoteResult.success(body)
                    }
                }
            }

            return RemoteResult(RemoteResult.Status.NULL,null,null)
//            return try {
//                val errorResponse = gson.fromJson(
//                    response.errorBody()!!.string(),
//                    ErrorResponse::class.java
//                )
//                val errorMessage = errorResponse.message
//
//                if (errorResponse.code == "auth/id-token-expired") {
//                    RemoteResult.refresh(errorMessage, null)
//                } else {
//                    error(errorMessage)
//                }
//            } catch (e: java.lang.Exception) {
//                error(" ${response.code()} ${response.message()}")
//            }


        }catch (e : Exception){
//            Log.d(TAG, "getResult: $")
            return error(e.message?: e.toString())
        }

    }
}