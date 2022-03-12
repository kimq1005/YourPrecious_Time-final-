package com.example.your_precioustime.activitylist_package.bus_activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.App
import com.example.your_precioustime.DB.BusFavroiteDataBase
import com.example.your_precioustime.mo_del.Bus
import com.example.your_precioustime.mo_del.Item
import com.example.your_precioustime.mo_del.StationBus
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.R
import com.example.your_precioustime.Retrofit.Coroutine_Manager
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Retrofit.Retrofit_Manager
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var busStationInfo_Adapater: BusStationInfo_Adpater


    private var retrofitInterface: Retrofit_InterFace =
        Retrofit_Client.getClient(Url.BUS_MAIN_URL).create(Retrofit_InterFace::class.java)

    private lateinit var busViewmodel: Bus_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        busViewmodel = ViewModelProvider(this).get(Bus_ViewModel::class.java)

        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        setMap()
//        setBusStationRecyclerView()
        CoroutineSetBusStationRecyclerView()
        SetmapView()
        BusrefreshView()


    }

    private fun BusrefreshView() {
        binding.myrefreshView.setOnRefreshListener {
//            setBusStationRecyclerView()
            CoroutineSetBusStationRecyclerView()
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

                val myLocationlatlng = LatLngBounds.Builder()

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
                Log.d(TAG, "onFailure:$t")

            }

        })


    }

    private fun CoroutineSetBusStationRecyclerView(){

        val stationName = intent.getStringExtra("stationName").toString()
        binding.BusStationName.text = stationName

        val stationNodeNumber = intent.getStringExtra("stationNodeNumber").toString()
        val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")

        CoroutineScope(Dispatchers.Main).launch {
           Coroutine_Manager.coroutineManager.CoroutinegetbusStationInfoCall(citycode,stationNodeNumber,
               mymodel ={busitem->
                   busStationInfo_Adapater = BusStationInfo_Adpater()

                   //viewmodelCall
                   busViewmodel.setStationInfoItem(busitem)


                   busViewmodel.stationinfoItem.observe(
                       this@MapsActivity,
                       Observer {  setbusitem->
                           binding.busreclerView.apply {
                               adapter = busStationInfo_Adapater
                               layoutManager = LinearLayoutManager(context)
                               busStationInfo_Adapater.submitList(setbusitem)
                           }
                       })
               })

        }

    }




    private fun setBusStationRecyclerView(){
        val stationName = intent.getStringExtra("stationName").toString()
        binding.BusStationName.text = stationName

        val stationNodeNumber = intent.getStringExtra("stationNodeNumber").toString()
        val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")

        Retrofit_Manager.retrofitManager.getbusStationInfoCall(citycode,stationNodeNumber,
            mymodel = { busitem->
                busStationInfo_Adapater = BusStationInfo_Adpater()

                //viewmodelCall
                busViewmodel.setStationInfoItem(busitem)


                busViewmodel.stationinfoItem.observe(
                    this@MapsActivity,
                    Observer {  setbusitem->
                        binding.busreclerView.apply {
                            adapter = busStationInfo_Adapater
                            layoutManager = LinearLayoutManager(context)
                            busStationInfo_Adapater.submitList(setbusitem)
                        }
                    })
            })
    }


}