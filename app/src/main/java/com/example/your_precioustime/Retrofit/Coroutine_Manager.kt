package com.example.your_precioustime.Retrofit

import android.util.Log
import android.view.View
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.mo_del.*

class Coroutine_Manager {

    companion object {
        val coroutineManager = Coroutine_Manager()
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


        val call = busretrofitInterface.CoroutineStationNameGet(
            citycode, stationName, nodeno
        )




        if (call.isSuccessful) {
            val stationitem = call.body()!!.body.items.item


            if (stationitem != null) {
                mymodel(stationitem)
            }


        } else {
            Log.d(TAG, "getCoroutinegetbusCall: 오류")
        }


    }

    //코루틴을 활용한 버스 정류장명(이름)에 대한 정보 가져오기 함수
    suspend fun CoroutinegetbusStationInfoCall(
        citycode: String,
        stationNodeNumber: String,
//        fastmodel: (List<Item> , List<Item>) -> Unit?,
        resultmodel: (List<ResultBusItem>) -> Unit?

    ) {
        val call = busretrofitInterface.CoroutineBusGet(citycode, stationNodeNumber)
        if (call.isSuccessful) {
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

            Log.d(Util.TAG, "\n 전체값 리스트 : $mutableItemList \n")


            //같은 버스의 정보가 연속으로 두개가 나와서 짝수, 홀수로 나눈다음 도착시간에 따른 우선순위에 따른 리스트를 가져옴
            val firstList = mutableItemList.filterIndexed { index, i ->

                index % 2 == 0
            }


            val secondList = mutableItemList.filterIndexed { index, item ->
                index % 2 == 1
            }

            //first list , second list를 나누고 빠른건 ResultList, 느린건 secondResultList에


            //같은 번호의 버스 중 가장 빠르게 오는 버스를 띄위기 위한 리스트 생성
            val FastResultList = mutableListOf<Item>()
            val LateResultList = mutableListOf<Item>()

            val ResultModel = mutableListOf<ResultBusItem>()



            firstList.forEach {
                val ARouteNo = it.routeno
                val AWaitstation = it.arrprevstationcnt
                val AWaitTime = it.arrtime


                secondList.forEach {
                    val BRouteNo = it.routeno
                    val BWaitstation = it.arrprevstationcnt

                    if (ARouteNo == BRouteNo) {
                        if (AWaitstation!! > BWaitstation!!) {
                            FastResultList.add(
                                Item(
                                    it.routeno,
                                    it.arrprevstationcnt,
                                    it.arrtime
                                )
                            )


                            LateResultList.add(Item(ARouteNo, AWaitstation, AWaitTime))

                            ResultModel.add(
                                ResultBusItem(
                                    it.routeno, it.arrprevstationcnt, it.arrtime,
                                    ARouteNo, AWaitstation, AWaitTime
                                )
                            )

                        } else {
                            FastResultList.add(Item(ARouteNo, AWaitstation, AWaitTime))
                            LateResultList.add(Item(it.routeno, it.arrprevstationcnt, it.arrtime))

                            ResultModel.add(
                                ResultBusItem(
                                    ARouteNo, AWaitstation, AWaitTime,
                                    it.routeno, it.arrprevstationcnt, it.arrtime
                                )
                            )

                        }
                    }

                }

//                fastmodel(FastResultList, LateResultList)
                resultmodel(ResultModel)


            }
            Log.d(TAG, "FastModel 확인 : $FastResultList  ")
            Log.d(TAG, "lateModel 확인 : $LateResultList  ")
            Log.d(TAG, "ResultModel 확인 : $ResultModel ")

        }

    }

    //코루틴을 활용한 지하철역 정보 가져오기 함수
    suspend fun CoroutinegetsubwayCall(
        statNm: String,
        snackview: View?,
        subtitle: View?,
        favoriteimage: View?,
        mymodel: (MutableList<SubwayItem>) -> Unit
    ) {
        val call = subwayretrofitInterface.CoroutineSUBWAYGET(
            statnNm = statNm
        )

        if (call.isSuccessful) {
            val subwaymodel = mutableListOf<SubwayItem>()
            val subwaycalllist = call.body()!!.realtimeArrivalList


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


}