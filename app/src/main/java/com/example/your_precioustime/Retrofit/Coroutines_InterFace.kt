package com.example.your_precioustime.Retrofit

import com.example.your_precioustime.mo_del.StationBus
import com.example.your_precioustime.Url
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


//코루틴으로 변경예정
interface Coroutines_InterFace {

    @GET(Url.BUS_NAME_SEARCH)
    suspend fun Coroutines_BUS_NAMEGET(
        @Query("cityCode") cityCode:String,
        @Query("nodeNm") staionName:String?,
    ) : Response<StationBus>


}