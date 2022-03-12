package com.example.your_precioustime.Retrofit

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.App
import com.example.your_precioustime.Model.SubwayModel.SubwayModel
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.activitylist_package.bus_activity.BusStationInfo_Adpater
import com.example.your_precioustime.activitylist_package.bus_activity.Bus_Station_Search_Adapter
import com.example.your_precioustime.activitylist_package.bus_activity.Bus_ViewModel
import com.example.your_precioustime.mo_del.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class Retrofit_Manager {

    companion object {
        val retrofitManager = Retrofit_Manager()
    }


    //지하철 API
    private var subwayretrofitInterface = Retrofit_Client.getJsonClienet(Url.SEOUL_SUBWAY_MAIN_URL)
        .create(Retrofit_InterFace::class.java)


    //버스 정류장 API
    private var busretrofitInterface: Retrofit_InterFace =
        Retrofit_Client.getClient(Url.BUS_MAIN_URL).create(Retrofit_InterFace::class.java)


    //코루틴을 활용한 버스 정류장명(이름) 가져오기 함수
    suspend fun getCoroutinegetbusCall(
        citycode: String,
        stationName: String?,
        nodeno: String?,
        mymodel: (List<StationItem>) -> Unit
    ) {


        val stationcalls = busretrofitInterface.CoroutineStationNameGet(
            citycode, stationName, nodeno
        )

        if (stationcalls.isSuccessful) {
            val stationitem = stationcalls.body()!!.body.items.item
            mymodel(stationitem)
        }


    }

    //버스 정류장명(이름) 가져오기 함수
    fun getbusCall(
        citycode: String,
        stationName: String?,
        nodeno: String?,
        mymodel: (List<StationItem>) -> Unit
    ) {


        val stationcalls = busretrofitInterface.StationNameGet(
            cityCode = citycode,
            staionName = stationName,
            nodeNo = nodeno
        )

        stationcalls.enqueue(object : retrofit2.Callback<StationBus> {
            override fun onResponse(call: Call<StationBus>, response: Response<StationBus>) {
                val body = response.body()
//                Log.d(TAG, " getbusCall() 함수 body : $body")

                body?.let { it ->
                    val stationitem = body.body.items.item
                    mymodel(stationitem)
//                    Log.d(TAG, "getbusCall() 함수 stationitem : $stationitem")
                }


            }

            override fun onFailure(call: Call<StationBus>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })
    }


    suspend fun CoroutinegetbusStationInfoCall(
        citycode: String,
        stationNodeNumber: String,
        mymodel: (List<Item>) -> Unit
    ){
        val call = busretrofitInterface.CoroutineBusGet(citycode,stationNodeNumber)
        if(call.isSuccessful){
            val mutableItemList = mutableListOf<Item>()
            val itemList = call.body()!!.body.items.item

            for (i in itemList.indices) {
                val busNm: String
                val waitbus: Int
                val waittime: Int

                busNm = itemList.get(i).routeno!!
                waitbus = itemList.get(i).arrprevstationcnt!!
                waittime = itemList.get(i).arrtime!!

                mutableItemList.add(
                    Item(
                        busNm, waitbus, waittime
                    )
                )

            }

            Log.d(TAG, "\n 전체값 리스트 : $mutableItemList \n")


            //같은 버스의 정보가 연속으로 두개가 나와서 짝수, 홀수로 나눈다음 도착시간에 따른 우선순위에 따른 리스트를 가져옴
            val firstList = mutableItemList.filterIndexed { index, i ->

                index % 2 == 0
            }


            val secondList = mutableItemList.filterIndexed { index, item ->
                index % 2 == 1
            }


            //같은 번호의 버스 중 가장 빠르게 오는 버스를 띄위기 위한 리스트 생성
            val ResultList = mutableListOf<Item>()


            firstList.forEach {
                val ARouteNo = it.routeno
                val AWaitstation = it.arrprevstationcnt
                val AWaitTime = it.arrtime


                secondList.forEach {
                    val BRouteNo = it.routeno
                    val BWaitstation = it.arrprevstationcnt

                    if (ARouteNo == BRouteNo) {
                        if (AWaitstation!! > BWaitstation!!) {
                            ResultList.add(
                                Item(
                                    it.routeno,
                                    it.arrprevstationcnt,
                                    it.arrtime
                                )
                            )

                        } else {
                            ResultList.add(Item(ARouteNo, AWaitstation, AWaitTime))
                        }
                    }

                }

                mymodel(ResultList)


            }

        }

    }


    //버스 정류장명(이름)에 대한 정보 가져오기 함수
    fun getbusStationInfoCall(
        citycode: String,
        stationNodeNumber: String,
        mymodel: (List<Item>) -> Unit
    ) {

        val call = busretrofitInterface.BusGet(citycode, stationNodeNumber)

        call.enqueue(object : retrofit2.Callback<Bus> {
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {

                val body = response.body()
//                Log.d(TAG, "getbusStationInfoCall() 함수 body : $body")

                body?.let {
                    val itemList = body.body.items.item
                    val mutableItemList = mutableListOf<Item>()

                    for (i in itemList.indices) {
                        val busNm: String
                        val waitbus: Int
                        val waittime: Int

                        busNm = itemList.get(i).routeno!!
                        waitbus = itemList.get(i).arrprevstationcnt!!
                        waittime = itemList.get(i).arrtime!!

                        mutableItemList.add(
                            Item(
                                busNm, waitbus, waittime
                            )
                        )

                    }

                    Log.d(TAG, "\n 전체값 리스트 : $mutableItemList \n")


                    val firstList = mutableItemList.filterIndexed { index, i ->

                        index % 2 == 0
                    }


                    val secondList = mutableItemList.filterIndexed { index, item ->
                        index % 2 == 1
                    }


                    //같은 번호의 버스 중 가장 빠르게 오는 버스를 띄위기 위한 리스트 생성
                    val ResultList = mutableListOf<Item>()


                    firstList.forEach {
                        val ARouteNo = it.routeno
                        val AWaitstation = it.arrprevstationcnt
                        val AWaitTime = it.arrtime


                        secondList.forEach {
                            val BRouteNo = it.routeno
                            val BWaitstation = it.arrprevstationcnt

                            if (ARouteNo == BRouteNo) {
                                if (AWaitstation!! > BWaitstation!!) {
                                    ResultList.add(
                                        Item(
                                            it.routeno,
                                            it.arrprevstationcnt,
                                            it.arrtime
                                        )
                                    )

                                } else {
                                    ResultList.add(Item(ARouteNo, AWaitstation, AWaitTime))
                                }
                            }

                        }

                        mymodel(ResultList)


                    }

                }

            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.d(TAG, "오류: $t")
            }

        })
    }


    //지하철역 정보 가져오기 함수
    fun getsubwayCall(
        statNm: String,
        snackview: View?,
        subtitle: View?,
        favoriteimage: View?,
        mymodel: (MutableList<SubwayItem>) -> Unit
    ) {

        val call = subwayretrofitInterface.SUBWAYGET(
            statnNm = statNm
        )

        call.enqueue(object : retrofit2.Callback<SubwayModel> {
            override fun onResponse(call: Call<SubwayModel>, response: Response<SubwayModel>) {
                val body = response.body()
                val subwaymodel = mutableListOf<SubwayItem>()

                body?.let {
                    val subwaycalllist = body.realtimeArrivalList

                    if (subwaycalllist != null) {
                        for (i in subwaycalllist.indices) {

                            val firstsubwayId = subwaycalllist.get(i).subwayId!!
                            val trainLineNm = subwaycalllist.get(i).trainLineNm
                            val bstatnNm = subwaycalllist.get(i).bstatnNm
                            val arvlMsg2 = subwaycalllist.get(i).arvlMsg2

                            subwaymodel.add(
                                SubwayItem(firstsubwayId, trainLineNm, bstatnNm, arvlMsg2)
                            )

                        }

                    } else {
                        if (snackview != null) {
                            Myobject.myobject.retrystation(snackview)
                            subtitle?.visibility = View.INVISIBLE
                            favoriteimage?.visibility = View.INVISIBLE
                        }
                    }


//                    Log.d(TAG, "getsubwayCall() 함수 subwaymodel : $subwaymodel")

                    for (i in subwaymodel.indices) {
                        when (subwaymodel[i].subwayId) {
                            "1001" -> {
                                subwaymodel[i].subwayId = "1"
                            }
                            "1002" -> {
                                subwaymodel[i].subwayId = "2"
                            }

                            "1003" -> {
                                subwaymodel[i].subwayId = "3"
                            }

                            "1004" -> {
                                subwaymodel[i].subwayId = "4"
                            }

                            "1005" -> {
                                subwaymodel[i].subwayId = "5"
                            }
                            "1006" -> {
                                subwaymodel[i].subwayId = "6"
                            }

                            "1007" -> {
                                subwaymodel[i].subwayId = "7"
                            }
                            "1008" -> {
                                subwaymodel[i].subwayId = "8"
                            }

                            "1009" -> {
                                subwaymodel[i].subwayId = "9"
                            }

                            "1063" -> {
                                subwaymodel[i].subwayId = "경"
                            }

                            "1065" -> {
                                subwaymodel[i].subwayId = "공"
                            }

                            "1067" -> {
                                subwaymodel[i].subwayId = "경춘"
                            }


                            "1075" -> {
                                subwaymodel[i].subwayId = "수"
                            }


                            "1077" -> {
                                subwaymodel[i].subwayId = "신"
                            }

                            "1091" -> {
                                subwaymodel[i].subwayId = "자"
                            }

                            "1092" -> {
                                subwaymodel[i].subwayId = "우"
                            }

                        }
                    }

                    mymodel(subwaymodel)


                }

            }

            override fun onFailure(call: Call<SubwayModel>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })

    }
}