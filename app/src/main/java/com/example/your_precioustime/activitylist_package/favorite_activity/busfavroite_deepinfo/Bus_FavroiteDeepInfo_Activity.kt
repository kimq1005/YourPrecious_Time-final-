package com.example.your_precioustime.activitylist_package.favorite_activity.busfavroite_deepinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.mo_del.Bus
import com.example.your_precioustime.mo_del.Item
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.ObjectManager.citycodeSaveClass
import com.example.your_precioustime.Retrofit.Coroutine_Manager
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Retrofit.Retrofit_Manager
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.activitylist_package.bus_activity.BusStationInfo_Adpater
import com.example.your_precioustime.activitylist_package.bus_activity.Bus_ViewModel
import com.example.your_precioustime.databinding.ActivityBusFavroiteDeepInfoBinding
import com.example.your_precioustime.roompackage.bus_room.Bus_RoomViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class Bus_FavroiteDeepInfo_Activity : AppCompatActivity(), CoroutineScope {
    private var busFavroiteDeepInfoBinding: ActivityBusFavroiteDeepInfoBinding? = null
    private val binding get() = busFavroiteDeepInfoBinding!!

    private lateinit var busStationInfo_Adapater: BusStationInfo_Adpater

    private lateinit var busViewmodel: Bus_ViewModel

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        busFavroiteDeepInfoBinding = ActivityBusFavroiteDeepInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()
        busViewmodel = ViewModelProvider(this).get(Bus_ViewModel::class.java)



        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        CoroutineBusfavoriteRecyclerViewSet()


        SetFreshView()



        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )


    }

    //???????????? ????????? ?????? ????????????(??????)??? ?????? ?????? ???????????? ?????? ???????????? ??? Recyclerview Set
    private fun CoroutineBusfavoriteRecyclerViewSet() {
        val favoritenodenum = intent.getStringExtra("favoritenodenum").toString()
        val favoriteStationName = intent.getStringExtra("favoriteStationName").toString()
        val citycode = intent.getStringExtra("citycode").toString()

        binding.BusStationName.text = favoriteStationName

        launch(coroutineContext) {
            try {
                withContext(Dispatchers.Main) {
                    Coroutine_Manager.coroutineManager.CoroutinegetbusStationInfoCall(citycode,
                        favoritenodenum,
                        resultmodel = { resultbusitem ->
                            busStationInfo_Adapater = BusStationInfo_Adpater()

                            //viewmodelCall
                            busViewmodel.setStationInfoItem(resultbusitem)

                            busViewmodel.stationinfoItem.observe(
                                this@Bus_FavroiteDeepInfo_Activity,
                                Observer { setbusitem ->
                                    binding.FravroitestationinfoRecyclerView.apply {
                                        adapter = busStationInfo_Adapater
                                        layoutManager = LinearLayoutManager(context)
                                        busStationInfo_Adapater.submitList(setbusitem)
                                    }
                                })
                        })
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@Bus_FavroiteDeepInfo_Activity, "Error", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }

    }


    private fun SetFreshView() {
        binding.BusFavroiteSwipe.setOnRefreshListener {
            CoroutineBusfavoriteRecyclerViewSet()
            binding.BusFavroiteSwipe.isRefreshing = false
        }
    }


}