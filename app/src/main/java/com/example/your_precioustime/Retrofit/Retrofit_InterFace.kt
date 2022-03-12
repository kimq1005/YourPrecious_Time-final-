package com.example.your_precioustime.Retrofit


import com.example.your_precioustime.Model.SubwayModel.SubwayModel
import com.example.your_precioustime.Url
import com.example.your_precioustime.mo_del.Bus
import com.example.your_precioustime.mo_del.StationBus
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Retrofit_InterFace {

    @GET(Url.BUS_GET_URL)
    fun BusGet(
//        @Header("serviceKey") serviceKey:String,
        @Query("cityCode") cityCode:String,
        @Query("nodeId") nodeId:String
    ): Call<Bus>


    @GET(Url.BUS_NAME_SEARCH)
    fun StationNameGet(
        @Query("cityCode") cityCode:String,
        @Query("nodeNm") staionName:String?,
        @Query("nodeNo") nodeNo:String?
    ) :Call<StationBus>


    @GET(Url.SUBWAY_PATH_URL)
    fun SUBWAYGET(
        @Path("KEY") KEY :String = "6749736c6b6b696d38365266596579",
        @Path("TYPE") TYPE:String = "json",
        @Path("SERVICE") SERVICE:String="realtimeStationArrival",
        @Path("START_INDEX") START_INDEX:Int = 0,
        @Path("END_INDEX") END_INDEX:Int = 5,
        @Path("statnNm") statnNm:String
    ):Call<SubwayModel>


    @GET(Url.BUS_NAME_SEARCH)
    suspend fun CoroutineStationNameGet(
        @Query("cityCode") cityCode:String,
        @Query("nodeNm") staionName:String?,
        @Query("nodeNo") nodeNo:String?
    ) :Response<StationBus>


    @GET(Url.BUS_GET_URL)
    suspend fun CoroutineBusGet(
//        @Header("serviceKey") serviceKey:String,
        @Query("cityCode") cityCode:String,
        @Query("nodeId") nodeId:String
    ): Response<Bus>




}

