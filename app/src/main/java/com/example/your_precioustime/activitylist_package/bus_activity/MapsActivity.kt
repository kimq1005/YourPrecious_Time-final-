package com.example.your_precioustime.activitylist_package.bus_activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.App
import com.example.your_precioustime.DB.BusFavroiteDataBase
import com.example.your_precioustime.Model.Bus
import com.example.your_precioustime.Model.Item
import com.example.your_precioustime.Model.StationBus
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.R
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.SecondActivity.DB.SubwayDB.TestFavoriteModel
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util
import com.example.your_precioustime.Util.Companion.TAG
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.your_precioustime.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import retrofit2.Call
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var busMaps_Adapater: BusMaps_Adpater
    lateinit var busFavoriteDB: BusFavroiteDataBase
    lateinit var activitybusfavoriteEntity: List<TestFavoriteModel>


    private var retrofitInterface: Retrofit_InterFace =
        Retrofit_Client.getClient(Url.BUS_MAIN_URL).create(Retrofit_InterFace::class.java)

    lateinit var busStationSearchAdapter: Bus_Station_Search_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        busFavoriteDB = BusFavroiteDataBase.getinstance(App.instance)!!

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        setMap()
        SetBusStationRecyclerView()
        busFavoriteGetAll()
        savemystation()
        SetmapView()
        BusrefreshView()


    }

    private fun BusrefreshView() {
        binding.myrefreshView.setOnRefreshListener {
            SetBusStationRecyclerView()
            binding.myrefreshView.isRefreshing = false
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }

    private fun setMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    //맵 정보 펼치기
    fun SetmapView() = with(binding) {
        val stationname = intent.getStringExtra("stationName")
        val stationnodenode = intent.getStringExtra("stationnodenode")
        Log.d(TAG, "SetmapView: $stationname , $stationnodenode")

        val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")

        val stationcalls = retrofitInterface.StationNameGet(
            cityCode = citycode,
            staionName = null,
            nodeNo = stationnodenode
        )

        stationcalls.enqueue(object : retrofit2.Callback<StationBus> {

            override fun onResponse(call: Call<StationBus>, response: Response<StationBus>) {
                val body = response.body()
                busMaps_Adapater = BusMaps_Adpater()

                val myLocationlatlng = LatLngBounds.Builder()
                Log.d(TAG, "setMapView: $body")
                busStationSearchAdapter = Bus_Station_Search_Adapter()

                body?.let { it ->
                    val hello = body.body.items.item

                    for (i in hello.indices) {
                        val xLocation = hello.get(i).gpslati?.toDouble()!!
                        val yLocation = hello.get(i).gpslong?.toDouble()!!
                        val mapStationname = hello.get(i).nodenm?.toString()!!
                        val position = LatLng(xLocation, yLocation)

                        val marker = MarkerOptions().position(position).title(mapStationname)
                        mMap.addMarker(marker)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 18f))
                        myLocationlatlng.include(position)

                    }

                }
            }

            override fun onFailure(call: Call<StationBus>, t: Throwable) {
                Log.d(Util.TAG, "onFailure:$t")

            }

        })


    }


    private fun SetBusStationRecyclerView() = with(binding) {

        val stationName = intent.getStringExtra("stationName").toString()
        binding.BusStationName.text = stationName

        val stationNodeNumber = intent.getStringExtra("stationNodeNumber").toString()
        val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")
        val call = retrofitInterface.BusGet(citycode, stationNodeNumber)

        call.enqueue(object : retrofit2.Callback<Bus> {
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                busMaps_Adapater = BusMaps_Adpater()

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

                    val secondList = hi.filterIndexed { index, item ->
                        index % 2 == 1
                    }
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

                        busreclerView.apply {
                            adapter = busMaps_Adapater
                            layoutManager = LinearLayoutManager(context)
                            busMaps_Adapater.submitList(ResultList)
                        }


                    }

                }

            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.d(TAG, "오류: $t")
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

                    Myobject.myobject.alreadyFavroiteSnackBar(binding.MapsActivity)

                } else {
                    Myobject.myobject.FavroiteSnackBar(binding.MapsActivity)
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
            BUSFravoriteInsert(hello)
        }
    }


}