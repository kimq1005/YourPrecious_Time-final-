package com.example.your_precioustime.activitylist_package.favorite_activity.busfavroite_deepinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.Model.Bus
import com.example.your_precioustime.Model.Item
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util
import com.example.your_precioustime.databinding.ActivityBusFavroiteDeepInfoBinding
import retrofit2.Call
import retrofit2.Response


//새로만든 엑티비티
class Bus_FavroiteDeepInfo_Activity : AppCompatActivity() {
    private var busFavroiteDeepInfoBinding: ActivityBusFavroiteDeepInfoBinding? = null
    private val binding get() = busFavroiteDeepInfoBinding!!

    private val retrofitInterface: Retrofit_InterFace =
        Retrofit_Client.getClient(Url.BUS_MAIN_URL).create(
            Retrofit_InterFace::class.java
        )
    lateinit var DFadapter: Bus_DeepFavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        busFavroiteDeepInfoBinding = ActivityBusFavroiteDeepInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setApiRecyclerView()
        binding.backbtn.setOnClickListener {
            onBackPressed()
        }

        binding.BusFavroiteSwipe.setOnRefreshListener {
            setApiRecyclerView()

            binding.BusFavroiteSwipe.isRefreshing = false
        }



        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )


    }

    private fun setApiRecyclerView() = with(binding) {
        val favoritenodenum = intent.getStringExtra("favoritenodenum").toString()
        val favoriteStationName = intent.getStringExtra("favoriteStationName").toString()
        val citycode = intent.getStringExtra("citycode").toString()


        BusStationName.text = favoriteStationName
        val call = retrofitInterface.BusGet(citycode, favoritenodenum)

        call.enqueue(object : retrofit2.Callback<Bus> {
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                Log.d(Util.TAG, "onResponse: ${response.body()}")
                val body = response.body()

                body?.let {
                    val wow = body.body.items.item
                    val mylist = mutableListOf<Item>()

                    for (i in wow.indices) {
                        val busNm: String
                        val waitbus: Int
                        val waittime: Int

                        busNm = wow.get(i).routeno!!
                        waitbus = wow.get(i).arrprevstationcnt!!
                        waittime = wow.get(i).arrtime!!


                        mylist.add(
                            Item(
                                busNm, waitbus, waittime
                            )
                        )

                        val firstList = mylist.filterIndexed { index, i ->
//                        Log.d(TAG, "인덱스값이 뭔지 확인하기 : $index , $i")
                            index % 2 == 0    //이건 그냥 말그대로 짝수만을 가져온거야
                        }

                        val secondList = mylist.filterIndexed { index, item ->
                            index % 2 == 1
                        }

                        val ResultList = mutableListOf<Item>()

                        firstList.forEach {
                            val ARouteNo = it.routeno
                            val AWaitstation = it.arrprevstationcnt
                            val AWaitTime = it.arrtime
                            var found = false


                            secondList.forEach {
                                val BRouteNo = it.routeno
                                val BWaitstation = it.arrprevstationcnt
                                val BWaitTime = it.arrtime

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

                            DFadapter = Bus_DeepFavoriteAdapter()

                            FravroitestationinfoRecyclerView.apply {
                                adapter = DFadapter
                                layoutManager = LinearLayoutManager(context)
                                DFadapter.submitList(ResultList)
                            }

                        }
                    }


                }
            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.d(Util.TAG, "onFailure: $t")
            }

        })
    }
}