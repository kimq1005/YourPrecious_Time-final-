package com.example.your_precioustime.activitylist_package.favorite_activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.activitylist_package.bus_activity.BusStationInfo_Adpater
import com.example.your_precioustime.App
import com.example.your_precioustime.DB.BusFavroiteDataBase
import com.example.your_precioustime.mo_del.Bus
import com.example.your_precioustime.mo_del.Item
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.R
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.SecondActivity.DB.*
import com.example.your_precioustime.SecondActivity.DB.SubwayDB.TestFavoriteModel
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.databinding.ActivityDeepStationInfoBinding
import com.example.your_precioustime.roompackage.bus_room.BusFavoriteEntity
import com.example.your_precioustime.roompackage.bus_room.Bus_RoomViewModel
import retrofit2.Call
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
class DeepStationInfoActivity : AppCompatActivity() {

    private var deepStationbinding: ActivityDeepStationInfoBinding? = null
    private val binding get() = deepStationbinding!!

    private lateinit var busStationInfo_Adpater: BusStationInfo_Adpater

    lateinit var busFavoriteDB: BusFavroiteDataBase
    lateinit var activitybusfavoriteEntity: List<TestFavoriteModel>

    private val retrofitInterface: Retrofit_InterFace =
        Retrofit_Client.getClient(Url.BUS_MAIN_URL).create(Retrofit_InterFace::class.java)

    ///////////////////////////////////////////////////////////////////
    private lateinit var busRoomviewmodel: Bus_RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deepStationbinding = ActivityDeepStationInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        busFavoriteDB = BusFavroiteDataBase.getinstance(App.instance)!!
        val stationName = intent.getStringExtra("stationName").toString()


        binding.BusStationName.text = stationName
        binding.backbtn.setOnClickListener {
            onBackPressed()
        }

        busRoomviewmodel = ViewModelProvider(
            this,
            Bus_RoomViewModel.Factory(application)
        ).get(Bus_RoomViewModel::class.java)

        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )

//        busFavoriteGetAll()

        //   savemystation()

        SetBusStationRecyclerView()




    }


    private fun savemystation() = with(binding) {

        countingstars.setOnClickListener {
            val stationName = intent.getStringExtra("stationName").toString()
            val stationNodeNumber = intent.getStringExtra("stationNodeNumber").toString()
            val stationNodeNode = intent.getStringExtra("stationnodenode").toString()
            val stationcitycode =
                citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")

            val hello = TestFavoriteModel(
                id = null,
                citycode = stationcitycode,
                stationnodenode = stationNodeNode,
                stationName = stationName,
                stationNodeNumber = stationNodeNumber
            )
//            BUSFravoriteInsert(hello)
        }
    }


    private fun SetBusStationRecyclerView() = with(binding) {
        val stationNodeNumber = intent.getStringExtra("stationNodeNumber").toString()

        val citycode: String = "31010"

//        val call = retrofitInterface.BusGet(citycode, stationNodeNumber)
        val call = retrofitInterface.BusGet("25", "DJB8001793")
        call.enqueue(object : retrofit2.Callback<Bus> {
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                busStationInfo_Adpater = BusStationInfo_Adpater()

                val body = response.body()

                body?.let {
                    val hello = body.body.items.item

                    val hi = mutableListOf<Item>()

                    for (i in hello.indices) {
                        val busNm: String
                        val waitbus: Int
                        val waittime: Int

                        busNm = hello.get(i).routeno!!
                        waitbus = hello.get(i).arrprevstationcnt!!
                        waittime = hello.get(i).arrtime!!

                        deepstationinfoRecyclerView.apply {
                            adapter = busStationInfo_Adpater
                            layoutManager = LinearLayoutManager(context)
                            busStationInfo_Adpater.submitList(hello)
                        }

                        hi.add(
                            Item(
                                busNm, waitbus, waittime
                            )
                        )

                    }
                    Log.d(TAG, "\n 전체값 리스트 : $hi \n")


                    val firstList = hi.filterIndexed { index, i ->

                        index % 2 == 0
                    }

//                    Log.d(TAG, "firstList: $firstList")

                    val secondList = hi.filterIndexed { index, item ->
                        index % 2 == 1
                    }

//                    Log.d(TAG, "secondList: $secondList")


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


                    }

                }

            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })


    }

    private fun BUSFravoriteInsert(busfavoriteEntity: TestFavoriteModel) {
        var businsertTask = (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {

                activitybusfavoriteEntity = busFavoriteDB.busFavoriteDAO().busFavoriteGetAll()

                val stationnameList = mutableListOf<String>()
                for (i in activitybusfavoriteEntity.indices) {
                    val stationname = activitybusfavoriteEntity.get(i).stationName
                    stationnameList.add(stationname)
                }
                if (binding.BusStationName.text !in stationnameList) {
                    busFavoriteDB.busFavoriteDAO().busFavoriteInsert(busfavoriteEntity)
                }


            }


            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                val stationnameList = mutableListOf<String>()

                for (i in activitybusfavoriteEntity.indices) {
                    val stationname = activitybusfavoriteEntity.get(i).stationName
                    stationnameList.add(stationname)
                }

                if (binding.BusStationName.text in stationnameList) {
                    Toast.makeText(
                        this@DeepStationInfoActivity,
                        "이미 즐겨찾기에 추가된 정류장입니다!",
                        Toast.LENGTH_SHORT
                    ).show()

                    Myobject.myobject.alreadyFavroiteSnackBar(binding.DeepStationINfoActivity)

                } else {
                    Toast.makeText(
                        this@DeepStationInfoActivity,
                        "즐겨찾기에 추가 되었습니다!",
                        Toast.LENGTH_SHORT
                    ).show()
                    Myobject.myobject.FavroiteSnackBar(binding.DeepStationINfoActivity)
                    binding.countingstars.setImageResource(R.drawable.shinigstar)
                }

            }
        }).execute()
    }

    private fun busFavoriteGetAll() {
        val busGetAllTask = (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                activitybusfavoriteEntity = busFavoriteDB.busFavoriteDAO().busFavoriteGetAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                val stationnameList = mutableListOf<String>()

                for (i in activitybusfavoriteEntity.indices) {
                    val stationname = activitybusfavoriteEntity.get(i).stationName
                    stationnameList.add(stationname)
                }

                if (binding.BusStationName.text in stationnameList) {
                    binding.countingstars.setImageResource(R.drawable.shinigstar)
                } else {
                    binding.countingstars.setImageResource(R.drawable.star)
                }

            }

        }).execute()
    }

    private fun busRoomFavroiteInsert() {
        binding.countingstars.setOnClickListener {

            val stationName = intent.getStringExtra("stationName").toString()
            val stationNodeNumber = intent.getStringExtra("stationNodeNumber").toString()
            val stationNodeNode = intent.getStringExtra("stationnodenode").toString()
            val stationcitycode =
                citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")

            busRoomviewmodel.businsert(
                citycode = stationcitycode,
                stationnodenode = stationNodeNode,
                stationName = stationName,
                stationNodeNumber = stationNodeNumber
            )



        }


    }

    private fun busFavoriteChecking() {

        busRoomviewmodel.busgetAll().observe(this, Observer { BusFavroiteEntity ->
            Log.d(TAG, "무야호홓옹오오ㅗㅇ : $BusFavroiteEntity ")

            val stationnameList = mutableListOf<String>()

            for( i in BusFavroiteEntity.indices ){
                val stationname = activitybusfavoriteEntity.get(i).stationName
                stationnameList.add(stationname)
            }

            if (binding.BusStationName.text in stationnameList) {
                binding.countingstars.setImageResource(R.drawable.shinigstar)
            } else {
                binding.countingstars.setImageResource(R.drawable.star)
            }
        })

    }


}