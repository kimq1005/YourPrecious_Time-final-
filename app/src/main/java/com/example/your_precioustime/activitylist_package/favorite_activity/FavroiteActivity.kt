package com.example.your_precioustime.activitylist_package.favorite_activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.App
import com.example.your_precioustime.DB.BusFavroiteDataBase
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.SecondActivity.DB.*
import com.example.your_precioustime.SecondActivity.DB.SubwayDB.TestFavoriteModel
import com.example.your_precioustime.databinding.ActivityFavroiteBinding
import com.example.your_precioustime.roompackage.bus_room.BusFavoriteEntity
import com.example.your_precioustime.roompackage.bus_room.Bus_RoomViewModel
import com.example.your_precioustime.roompackage.subway_room.SubwayFavoriteEntity
import com.example.your_precioustime.roompackage.subway_room.Subway_FavoriteViewModel

@SuppressLint("StaticFieldLeak")
class FavroiteActivity : AppCompatActivity(), OnBusFavroiteListDeleteInterFace, OnSubwayFavoriteListDeleteInterFace {

    private var favroiteBinding: ActivityFavroiteBinding? = null
    private val binding get() = favroiteBinding!!

    lateinit var busFavroiteadapter: Bus_FavroiteAdapter
    lateinit var subwayfavoriteAdpater: Subway_FavoriteAdpater

    private lateinit var busroomViewModel: Bus_RoomViewModel
    private lateinit var subwayFavoriteviewmodel: Subway_FavoriteViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favroiteBinding = ActivityFavroiteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backwowbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        busroomViewModel=ViewModelProvider(this , Bus_RoomViewModel.Factory(application)).get(Bus_RoomViewModel::class.java)
        subwayFavoriteviewmodel = ViewModelProvider(this , Subway_FavoriteViewModel.Factory(application)).get(Subway_FavoriteViewModel::class.java)

        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )

        BusFavroiteRecyclerViewSet()
        SubwayFavoriteRecyclerViewSet()


    }

    //ViewModel , LiveData 도입 및 RecyclerView Set
    private fun BusFavroiteRecyclerViewSet(){
        busFavroiteadapter = Bus_FavroiteAdapter(this)

        busroomViewModel.busgetAll().observe(this, Observer { busfavoriteEntity->
            binding.BusFavoriteRecyclerView.apply {
                adapter = busFavroiteadapter
                layoutManager = LinearLayoutManager(context)
                busFavroiteadapter.submitlist(busfavoriteEntity)
            }
        })
    }


    //ViewModel , LiveData 도입 및 RecyclerView Set
    private fun SubwayFavoriteRecyclerViewSet(){
        subwayfavoriteAdpater = Subway_FavoriteAdpater(this)

        subwayFavoriteviewmodel.subwaygetAll().observe(this, Observer { subwayFavoriteEntity->
            binding.SubwayFvRecylerView.apply{
                adapter = subwayfavoriteAdpater
                layoutManager = LinearLayoutManager(context)
                subwayfavoriteAdpater.submitList(subwayFavoriteEntity)
            }
        })

    }


    private fun busfavoriteDelete(busFavoriteEntity: BusFavoriteEntity){
        busroomViewModel.busdelete(busFavoriteEntity)

    }


    private fun subwayfavoriteDelete(subwayFavoriteEntity: SubwayFavoriteEntity){
        subwayFavoriteviewmodel.subwayDelete(subwayFavoriteEntity)
    }


    override fun onDeleteBusFavoriteList(busFavoriteEntity: BusFavoriteEntity) {
        busfavoriteDelete(busFavoriteEntity)
    }

    override fun onDeleteSubwayFavoriteList(subwayFavoriteEntity: SubwayFavoriteEntity) {
        subwayfavoriteDelete(subwayFavoriteEntity)
    }


}