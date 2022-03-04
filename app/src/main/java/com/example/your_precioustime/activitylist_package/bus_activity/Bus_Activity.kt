package com.example.your_precioustime.activitylist_package.bus_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.mo_del.StationBus
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.databinding.ActivityBusBinding
import retrofit2.Call
import retrofit2.Response

class Bus_Activity : AppCompatActivity() {

    private var busBinding: ActivityBusBinding? = null
    private val binding get() = busBinding!!

    lateinit var busStationSearchAdapter: Bus_Station_Search_Adapter
    private var retrofitInterface: Retrofit_InterFace =
        Retrofit_Client.getClient(Url.BUS_MAIN_URL).create(Retrofit_InterFace::class.java)

    private lateinit var bus_ViewModel: Bus_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        busBinding = ActivityBusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")
        setLiveDataRecyclerView(citycode, null)
        bus_ViewModel = ViewModelProvider(this).get(Bus_ViewModel::class.java)



        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )

        ClickSearchBtn()
        //MVVMGOGO
    }


    //LiveData, ViewModel 사용한 RecyclerView
    private fun setLiveDataRecyclerView(citycode: String, stationName: String?) = with(binding) {


        val stationcalls = retrofitInterface.StationNameGet(
            cityCode = citycode,
            staionName = stationName,
            null
        )

        stationcalls.enqueue(object : retrofit2.Callback<StationBus> {
            override fun onResponse(call: Call<StationBus>, response: Response<StationBus>) {
                val body = response.body()

                busStationSearchAdapter = Bus_Station_Search_Adapter()

                body?.let { it ->
                    val hello = body.body.items.item

                    bus_ViewModel.setStationBusItem(hello)

                    bus_ViewModel.stationItem.observe(
                        this@Bus_Activity, Observer {
                            busRecyclerView.apply {
                                adapter = busStationSearchAdapter
                                layoutManager = LinearLayoutManager(context)
                                busStationSearchAdapter.submitList(it)
                            }
                        }
                    )


                }
            }

            override fun onFailure(call: Call<StationBus>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })
    }

    private fun ClickSearchBtn() = with(binding) {
        clickhere.setOnClickListener {
            val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")
            val StationEditName = SearchEditText.text.toString()
            setLiveDataRecyclerView(citycode, StationEditName)
        }

    }


}