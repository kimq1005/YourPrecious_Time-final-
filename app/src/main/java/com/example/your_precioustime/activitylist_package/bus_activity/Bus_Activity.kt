package com.example.your_precioustime.activitylist_package.bus_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.mo_del.StationBus
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.Retrofit.Coroutine_Manager
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Retrofit.Retrofit_Manager
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.databinding.ActivityBusBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class Bus_Activity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var busBinding: ActivityBusBinding? = null
    private val binding get() = busBinding!!

    lateinit var busStationSearchAdapter: Bus_Station_Search_Adapter


    private lateinit var bus_ViewModel: Bus_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        busBinding = ActivityBusBinding.inflate(layoutInflater)
        setContentView(binding.root)


        job = Job()

        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")
        CorutinesetLiveDataRecyclerView(citycode, null)
//        setLiveDataRecyclerView(citycode, null)
        bus_ViewModel = ViewModelProvider(this).get(Bus_ViewModel::class.java)



        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )

        ClickSearchBtn()

    }


    //코루틴을 활용한 recyclerViewSet
    private fun CorutinesetLiveDataRecyclerView(citycode: String, stationName: String?) {

        launch(coroutineContext) {
            try {
                withContext(Dispatchers.Main) {
                    Coroutine_Manager.coroutineManager.getCoroutinegetbusCall(citycode,
                        stationName,
                        null,
                        mymodel = { stationitem ->
                            busStationSearchAdapter = Bus_Station_Search_Adapter()

                            bus_ViewModel.setStationBusItem(stationitem)

                            bus_ViewModel.stationItem.observe(this@Bus_Activity, Observer {
                                binding.busRecyclerView.apply {
                                    adapter = busStationSearchAdapter
                                    layoutManager = LinearLayoutManager(context)
                                    busStationSearchAdapter.submitList(it)
                                }
                            })

                        })
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@Bus_Activity , "정류장(역)을 재입력 해주세요." , Toast.LENGTH_SHORT).show()
            }
        }


    }

    //버스 정류장명(이름) 가져오기 함수 및 LiveData, ViewModel 사용한 RecyclerView Set
    private fun setLiveDataRecyclerView(citycode: String, stationName: String?) {
        Retrofit_Manager.retrofitManager.getbusCall(citycode, stationName, null,
            mymodel = { stationitem ->
                busStationSearchAdapter = Bus_Station_Search_Adapter()

                bus_ViewModel.setStationBusItem(stationitem)

                bus_ViewModel.stationItem.observe(
                    this@Bus_Activity, Observer {
                        binding.busRecyclerView.apply {
                            adapter = busStationSearchAdapter
                            layoutManager = LinearLayoutManager(context)
                            busStationSearchAdapter.submitList(it)
                        }
                    }
                )

            })
    }


    private fun ClickSearchBtn() {
        binding.clickhere.setOnClickListener {
            val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")
            val StationEditName = binding.SearchEditText.text.toString()
//            setLiveDataRecyclerView(citycode, StationEditName)
            CorutinesetLiveDataRecyclerView(citycode, StationEditName)
        }

    }


}