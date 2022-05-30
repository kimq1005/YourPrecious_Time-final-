package com.example.your_precioustime.testpackage

data class RemoteResult<out T> (
    val status : Status,
    val data : T?,
    val message : String?
        ){

    enum class Status{
        SUCCESS,
        ERROR,
        REFRESH,
        NULL
    }

    companion object {
        fun<T> success(data : T): RemoteResult<T>{
            return RemoteResult(Status.SUCCESS, data , null)
        }


        fun<T> error(message:String, data:T? = null):RemoteResult<T>{
            return RemoteResult(Status.ERROR , data, message)
        }

        fun <T> refresh(message: String, data: T? = null): RemoteResult<T> {
            return RemoteResult(Status.REFRESH, data, message)
        }
    }


}


