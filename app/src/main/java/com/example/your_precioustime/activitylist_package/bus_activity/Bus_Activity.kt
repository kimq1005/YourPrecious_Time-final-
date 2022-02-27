package com.example.your_precioustime.activitylist_package.bus_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.Model.StationBus
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util
import com.example.your_precioustime.databinding.ActivityBusBinding
import retrofit2.Call
import retrofit2.Response

class Bus_Activity : AppCompatActivity() {

    private var busBinding: ActivityBusBinding? = null
    private val binding get() = busBinding!!

    lateinit var busStationSearchAdapter: Bus_Station_Search_Adapter
    private var retrofitInterface: Retrofit_InterFace = Retrofit_Client.getClient(Url.BUS_MAIN_URL).create(Retrofit_InterFace::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        busBinding = ActivityBusBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")

        SetRecyclerView(citycode, null)

        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

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

    private fun ClickSearchBtn() = with(binding) {
        clickhere.setOnClickListener {
            val citycode = citycodeSaveClass.citycodeSaveClass.Loadcitycode("citycode", "citycode")
            val StationEditName = SearchEditText.text.toString()
            SetRecyclerView(citycode, StationEditName)
        }

    }


    fun SetRecyclerView(citycode: String, stationName: String?) = with(binding) {

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
                    busRecyclerView.apply {
                        adapter = busStationSearchAdapter
                        layoutManager = LinearLayoutManager(context)
                        busStationSearchAdapter.submitList(hello)
                    }
                }
            }

            override fun onFailure(call: Call<StationBus>, t: Throwable) {
                Log.d(Util.TAG, "onFailure:$t")

            }

        })


    }

}