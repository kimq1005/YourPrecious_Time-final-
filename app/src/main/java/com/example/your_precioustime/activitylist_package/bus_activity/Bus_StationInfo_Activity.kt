package com.example.your_precioustime.activitylist_package.bus_activity

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.R
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Retrofit.Retrofit_Manager
import com.example.your_precioustime.SecondActivity.DB.SubwayDB.TestFavoriteModel
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util
import com.example.your_precioustime.databinding.ActivityBusStationInfoBinding
import com.example.your_precioustime.roompackage.bus_room.Bus_RoomViewModel
import retrofit2.Call
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
class Bus_StationInfo_Activity : AppCompatActivity() {

    private var busStationinfoBinding: ActivityBusStationInfoBinding? = null
    private val binding get() = busStationinfoBinding!!


    private lateinit var busStationInfo_Adapater: BusStationInfo_Adpater


    private var retrofitInterface: Retrofit_InterFace =
        Retrofit_Client.getClient(Url.BUS_MAIN_URL).create(Retrofit_InterFace::class.java)

    private lateinit var busViewmodel: Bus_ViewModel
    private lateinit var busRoomviewmodel: Bus_RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        busStationinfoBinding = ActivityBusStationInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        busViewmodel = ViewModelProvider(this).get(Bus_ViewModel::class.java)
        busRoomviewmodel = ViewModelProvider(
            this,
            Bus_RoomViewModel.Factory(application)
        ).get(Bus_RoomViewModel::class.java)

        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        binding.locationcardView.setOnClickListener {
            val stationName = intent.getStringExtra("stationName")
            val stationnodenode = intent.getStringExtra("stationnodenode")
            val stationNodeNumnder = intent.getStringExtra("stationNodeNumber")

            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("stationName", stationName)
            intent.putExtra("stationnodenode", stationnodenode)
            intent.putExtra("stationNodeNumber", stationNodeNumnder)
            startActivity(intent)
        }


        SetFreshView()

        LiveDataSetBusStationRecyclerView()
        busFavoriteChecking()



        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )


    }

    //새로고침
    private fun SetFreshView() {
        binding.BusInfoStationSwipeLayout.setOnRefreshListener {
            LiveDataSetBusStationRecyclerView()
            binding.BusInfoStationSwipeLayout.isRefreshing = false
        }
    }

    //버스 정류장명(이름)에 대한 정보 가져오기 함수 불러오기 및 Recyclerview Set
    private fun LiveDataSetBusStationRecyclerView() {
        val stationName = intent.getStringExtra("stationName").toString()
        binding.BusInfoTitleTextView.text = stationName
        binding.titleviewTitleTextView.text = stationName
        val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")
        val stationNodeNumber = intent.getStringExtra("stationNodeNumber").toString()


        Retrofit_Manager.retrofitManager.getbusStationInfoCall(citycode,stationNodeNumber,
        mymodel = { busitem->
            busStationInfo_Adapater = BusStationInfo_Adpater()

            //viewmodelCall
            busViewmodel.setStationInfoItem(busitem)


            busViewmodel.stationinfoItem.observe(
                this@Bus_StationInfo_Activity,
                Observer {  setbusitem->
                    binding.BusStationInfoRecyclerView.apply {
                        adapter = busStationInfo_Adapater
                        layoutManager = LinearLayoutManager(context)
                        busStationInfo_Adapater.submitList(setbusitem)
                    }
                })
        })
    }



    //버스 즐겨찾기 추가
    private fun busRoomFavroiteInsert() {
        binding.favroiteaddImage.setOnClickListener {

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

            Myobject.myobject.FavroiteSnackBar(binding.BusStationInfoActivity)
        }


    }

    //버스 즐겨찾기 확인
    private fun busFavoriteChecking() {

        busRoomviewmodel.busgetAll().observe(this, Observer { BusFavroiteEntity ->

            val stationnameList = mutableListOf<String>()

            for (i in BusFavroiteEntity.indices) {
                val stationname = BusFavroiteEntity.get(i).stationName
                stationnameList.add(stationname)
            }

            if (binding.BusInfoTitleTextView.text in stationnameList) {
                binding.favroiteaddImage.setImageResource(R.drawable.fullstar)

                binding.favroiteaddImage.setOnClickListener {
                    Myobject.myobject.alreadyFavroiteSnackBar(binding.BusStationInfoActivity)
                }

            } else {
                binding.favroiteaddImage.setImageResource(R.drawable.whitestar)
                busRoomFavroiteInsert()


            }


        })

    }


}