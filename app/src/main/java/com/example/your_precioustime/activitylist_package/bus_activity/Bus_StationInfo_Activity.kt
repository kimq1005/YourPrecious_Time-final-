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
    lateinit var busFavoriteDB: BusFavroiteDataBase

    lateinit var activitybusfavoriteEntity: List<TestFavoriteModel>

    private var retrofitInterface: Retrofit_InterFace =
        Retrofit_Client.getClient(Url.BUS_MAIN_URL).create(Retrofit_InterFace::class.java)

    private lateinit var busViewmodel: Bus_ViewModel
    private lateinit var busRoomviewmodel: Bus_RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        busStationinfoBinding = ActivityBusStationInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        busFavoriteDB = BusFavroiteDataBase.getinstance(App.instance)!!
        busViewmodel = ViewModelProvider(this).get(Bus_ViewModel::class.java)
        busRoomviewmodel = ViewModelProvider(this, Bus_RoomViewModel.Factory(application)).get(Bus_RoomViewModel::class.java)

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

        binding.sharecardView.setOnClickListener {
            //
        }


        SetFreshView()
        LiveDataSetBusStationRecyclerView()

        busRoomFavroiteInsert()
        busFavoriteChecking()



//        busFavoriteGetAll()
//        savemystation()


        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )


    }

    private fun SetFreshView() {
        binding.BusInfoStationSwipeLayout.setOnRefreshListener {
            SetBusStationRecyclerView()
            binding.BusInfoStationSwipeLayout.isRefreshing = false
        }
    }

    private fun LiveDataSetBusStationRecyclerView() = with(binding) {
        val stationName = intent.getStringExtra("stationName").toString()
        binding.BusInfoTitleTextView.text = stationName
        binding.titleviewTitleTextView.text = stationName

        val stationNodeNumber = intent.getStringExtra("stationNodeNumber").toString()
        val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")

        val call = retrofitInterface.BusGet(citycode, stationNodeNumber)
        call.enqueue(object : retrofit2.Callback<Bus> {
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                busStationInfo_Adapater = BusStationInfo_Adpater()

                val body = response.body()

                body?.let {
                    val itemList = body.body.items.item
                    val hi = mutableListOf<Item>()
                    for (i in itemList.indices) {
                        val busNm: String
                        val waitbus: Int
                        val waittime: Int

                        busNm = itemList.get(i).routeno!!
                        waitbus = itemList.get(i).arrprevstationcnt!!
                        waittime = itemList.get(i).arrtime!!

                        hi.add(
                            Item(
                                busNm, waitbus, waittime
                            )
                        )

                    }
                    Log.d(Util.TAG, "\n 전체값 리스트 : $hi \n")


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

                        //viewmodelCall
                        busViewmodel.setStationInfoItem(ResultList)


                        busViewmodel.stationinfoItem.observe(
                            this@Bus_StationInfo_Activity,
                            Observer { it ->
                                BusStationInfoRecyclerView.apply {
                                    adapter = busStationInfo_Adapater
                                    layoutManager = LinearLayoutManager(context)
                                    busStationInfo_Adapater.submitList(it)
                                }
                            })


                    }

                }

            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.d(Util.TAG, "오류: $t")
            }

        })
    }


    private fun SetBusStationRecyclerView() = with(binding) {

        val stationName = intent.getStringExtra("stationName").toString()
        binding.BusInfoTitleTextView.text = stationName
        binding.titleviewTitleTextView.text = stationName

        val stationNodeNumber = intent.getStringExtra("stationNodeNumber").toString()
        val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")

        val call = retrofitInterface.BusGet(citycode, stationNodeNumber)
        call.enqueue(object : retrofit2.Callback<Bus> {
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                busStationInfo_Adapater = BusStationInfo_Adpater()

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
                    Log.d(Util.TAG, "\n 전체값 리스트 : $hi \n")


                    val firstList = hi.filterIndexed { index, i ->

                        index % 2 == 0
                    }


                    val secondList = hi.filterIndexed { index, item ->
                        index % 2 == 1
                    }


                    val ResultList = mutableListOf<Item>()
                    val SecondResultList = mutableListOf<Item>()


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

                        BusStationInfoRecyclerView.apply {
                            adapter = busStationInfo_Adapater
                            layoutManager = LinearLayoutManager(context)
                            busStationInfo_Adapater.submitList(ResultList)
                        }


                    }

                }

            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.d(Util.TAG, "오류: $t")
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
                if (binding.BusInfoTitleTextView.text !in stationnameList) {
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

                if (binding.BusInfoTitleTextView.text in stationnameList) {
                    Myobject.myobject.alreadyFavroiteSnackBar(binding.BusStationInfoActivity)

                } else {
                    Myobject.myobject.FavroiteSnackBar(binding.BusStationInfoActivity)
                    binding.favroiteaddImage.setImageResource(R.drawable.fullstar)
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

                if (binding.BusInfoTitleTextView.text in stationnameList) {
                    binding.favroiteaddImage.setImageResource(R.drawable.fullstar)
                } else {
                    binding.favroiteaddImage.setImageResource(R.drawable.whitestar)
                }

            }

        }).execute()
    }



    private fun savemystation() = with(binding) {

        favroiteaddImage.setOnClickListener {

            val stationName = intent.getStringExtra("stationName").toString()
            val stationNodeNumber = intent.getStringExtra("stationNodeNumber").toString()
            val stationNodeNode = intent.getStringExtra("stationnodenode").toString()
            val stationcitycode =
                citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")

            val FavroiteModel = TestFavoriteModel(
                id = null,
                citycode = stationcitycode,
                stationnodenode = stationNodeNode,
                stationName = stationName,
                stationNodeNumber = stationNodeNumber
            )
            BUSFravoriteInsert(FavroiteModel)
        }
    }

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

        }


    }

    private fun busFavoriteChecking() {

        busRoomviewmodel.busgetAll().observe(this, Observer { BusFavroiteEntity ->
            Log.d(Util.TAG, "무야호홓옹오오ㅗㅇ : $BusFavroiteEntity ")

            val stationnameList = mutableListOf<String>()

            for (i in BusFavroiteEntity.indices) {
                val stationname = BusFavroiteEntity.get(i).stationName
                stationnameList.add(stationname)
            }

            if (binding.BusInfoTitleTextView.text in stationnameList) {
                binding.favroiteaddImage.setImageResource(R.drawable.fullstar)
            } else {
                binding.favroiteaddImage.setImageResource(R.drawable.whitestar)
            }



        })

    }
}