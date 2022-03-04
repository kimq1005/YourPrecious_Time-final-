package com.example.your_precioustime.activitylist_package.subway_activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.mo_del.SubwayItem
import com.example.your_precioustime.Model.SubwayModel.SubwayModel
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.R
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Retrofit.Retrofit_Manager
import com.example.your_precioustime.SecondActivity.DB.SubwayDataBase
import com.example.your_precioustime.SecondActivity.DB.SubwayNameEntity
import com.example.your_precioustime.Url.Companion.SEOUL_SUBWAY_MAIN_URL
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.databinding.ActivitySubwayBinding
import com.example.your_precioustime.roompackage.subway_room.SubwayFavoriteEntity
import com.example.your_precioustime.roompackage.subway_room.Subway_FavoriteViewModel
import retrofit2.Call
import retrofit2.Response


@SuppressLint("StaticFieldLeak")
class Subway_Activity : AppCompatActivity() {

    private var subwayBinding: ActivitySubwayBinding? = null
    private val binding get() = subwayBinding!!

    private lateinit var subwayViewModel: SubwayViewModel


    lateinit var subwayAdapter: SubwayAdapter
    private lateinit var subwayRoomViewModel: Subway_FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subwayBinding = ActivitySubwayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subwayViewModel = ViewModelProvider(this).get(SubwayViewModel::class.java)
        subwayRoomViewModel =
            ViewModelProvider(this, Subway_FavoriteViewModel.Factory(application)).get(
                Subway_FavoriteViewModel::class.java
            )


        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )



        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }




        binding.subwaySwipe.setOnRefreshListener {
            val searchtext = binding.SearchEditText.text.toString()
            binding.subtitleTextView.text = searchtext
            binding.subwayfavroiteAddImageView.visibility = View.VISIBLE
            binding.subtitleTextView.visibility = View.VISIBLE
            subwaySetRecyclerView(searchtext)
            binding.subwaySwipe.isRefreshing = false
        }

        binding.SubwaySearchBtn.setOnClickListener {
            subwayFavoriteChecking()
            val searchtext = binding.SearchEditText.text.toString()
            binding.subtitleTextView.text = searchtext
            binding.subwayfavroiteAddImageView.visibility = View.VISIBLE
            binding.subtitleTextView.visibility = View.VISIBLE
            binding.secondunderline.visibility = View.VISIBLE
            subwaySetRecyclerView(searchtext)

        }


    }


    //RecyclerViewSet
    private fun subwaySetRecyclerView(statNm: String) {
        val snackview = binding.subwayActivity
        val subtitle = binding.subtitleTextView
        val favoriteimage = binding.subwayfavroiteAddImageView

        Retrofit_Manager.retrofitManager.getsubwayCall(
            statNm,
            snackview,
            subtitle,
            favoriteimage,
            mymodel = { subwaymodel ->
                subwayAdapter = SubwayAdapter()

                subwayViewModel.setSubwayItem(subwaymodel)
                subwayViewModel.subwayItem.observe(this, Observer {
                    binding.subwayRecyclerView.apply {
                        adapter = subwayAdapter
                        layoutManager = LinearLayoutManager(this@Subway_Activity)
                        subwayAdapter.submitList(it)
                    }
                })

            })

    }


    private fun subwayRoomFavroiteInsert() {
        binding.subwayfavroiteAddImageView.setOnClickListener {
            val subwayname = binding.subtitleTextView.text.toString()
            subwayRoomViewModel.subwayInsert(subwayname)

            Myobject.myobject.FavroiteSnackBar(binding.subwayActivity)
        }
    }

    private fun subwayFavoriteChecking() {

        subwayRoomViewModel.subwaygetAll().observe(this, Observer { SubwayFavoriteEntity ->
            val stationnameList = mutableListOf<String>()

            for (i in SubwayFavoriteEntity.indices) {
                val stationname = SubwayFavoriteEntity.get(i).subwayName
                stationnameList.add(stationname)
            }

            if (binding.subtitleTextView.text in stationnameList) {
                binding.subwayfavroiteAddImageView.setImageResource(R.drawable.shinigstar)

                binding.subwayfavroiteAddImageView.setOnClickListener {
                    Myobject.myobject.alreadyFavroiteSnackBar(binding.subwayActivity)
                }
            } else {
                binding.subwayfavroiteAddImageView.setImageResource(R.drawable.star)
                subwayRoomFavroiteInsert()
            }


        })
    }


}