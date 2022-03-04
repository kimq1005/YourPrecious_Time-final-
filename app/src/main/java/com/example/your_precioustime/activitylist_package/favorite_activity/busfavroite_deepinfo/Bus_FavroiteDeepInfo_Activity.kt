package com.example.your_precioustime.activitylist_package.favorite_activity.busfavroite_deepinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.mo_del.Bus
import com.example.your_precioustime.mo_del.Item
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Retrofit.Retrofit_Manager
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.activitylist_package.bus_activity.Bus_ViewModel
import com.example.your_precioustime.databinding.ActivityBusFavroiteDeepInfoBinding
import com.example.your_precioustime.roompackage.bus_room.Bus_RoomViewModel
import retrofit2.Call
import retrofit2.Response


//새로만든 엑티비티
class Bus_FavroiteDeepInfo_Activity : AppCompatActivity() {
    private var busFavroiteDeepInfoBinding: ActivityBusFavroiteDeepInfoBinding? = null
    private val binding get() = busFavroiteDeepInfoBinding!!

    lateinit var DFadapter: Bus_DeepFavoriteAdapter

    private lateinit var busViewmodel: Bus_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        busFavroiteDeepInfoBinding = ActivityBusFavroiteDeepInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        busViewmodel = ViewModelProvider(this).get(Bus_ViewModel::class.java)



        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        BusfavoriteRecyclerViewSet()


        SetFreshView()



        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )


    }


    //버스 정류장명(이름)에 대한 정보 가져오기 함수 불러오기 및 Recyclerview Set
    private fun BusfavoriteRecyclerViewSet(){
        val favoritenodenum = intent.getStringExtra("favoritenodenum").toString()
        val favoriteStationName = intent.getStringExtra("favoriteStationName").toString()
        val citycode = intent.getStringExtra("citycode").toString()

        binding.BusStationName.text = favoriteStationName

        Retrofit_Manager.retrofitManager.getbusStationInfoCall(citycode , favoritenodenum,
        mymodel = { busitem->
            DFadapter = Bus_DeepFavoriteAdapter()

            busViewmodel.setStationInfoItem(busitem)

            busViewmodel.stationinfoItem.observe(
                this@Bus_FavroiteDeepInfo_Activity,
                Observer { setbusitem->
                    binding.FravroitestationinfoRecyclerView.apply {
                        adapter = DFadapter
                        layoutManager = LinearLayoutManager(context)
                        DFadapter.submitlist(setbusitem)
                    }
                })

        })
    }

    private fun SetFreshView() {
        binding.BusFavroiteSwipe.setOnRefreshListener {
            BusfavoriteRecyclerViewSet()
            binding.BusFavroiteSwipe.isRefreshing = false
        }
    }

}