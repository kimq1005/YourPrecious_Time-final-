package com.example.your_precioustime.activitylist_package.favorite_activity.subwayfavroite_deepinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.activitylist_package.subway_activity.SubwayAdapter
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.Retrofit.Coroutine_Manager
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Retrofit.Retrofit_Manager
import com.example.your_precioustime.Url
import com.example.your_precioustime.activitylist_package.subway_activity.SubwayViewModel
import com.example.your_precioustime.databinding.ActivitySubwayFavoriteDeepInfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Subway_FravoriteDeepInfo_Activity : AppCompatActivity() {

    private var SubwayFavroiteDeepInfoBinding: ActivitySubwayFavoriteDeepInfoBinding? = null
    private val binding get() = SubwayFavroiteDeepInfoBinding!!


    lateinit var subwayAdapter: SubwayAdapter
    private lateinit var subwayViewModel: SubwayViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SubwayFavroiteDeepInfoBinding =
            ActivitySubwayFavoriteDeepInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subwayViewModel = ViewModelProvider(this).get(SubwayViewModel::class.java)


        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        binding.SubwayFavroiteSwipe.setOnRefreshListener {
//            setRecyclearView()
            CoroutinesetRecyclearView()
            binding.SubwayFavroiteSwipe.isRefreshing = false
        }

//        setRecyclearView()
        CoroutinesetRecyclearView()

        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )


    }

    private fun CoroutinesetRecyclearView(){
        val subwayname = intent.getStringExtra("subwayname").toString()
        binding.SubwayStationName.text = subwayname
        CoroutineScope(Dispatchers.Main).launch {
            Coroutine_Manager.coroutineManager.CoroutinegetsubwayCall(subwayname,null,null,null,
                mymodel = { subwaymodel ->
                    subwayAdapter = SubwayAdapter()

                    subwayViewModel.setSubwayItem(subwaymodel)
                    subwayViewModel.subwayItem.observe(this@Subway_FravoriteDeepInfo_Activity , Observer {
                        binding.SubwayFVDeepInFoRecyclerView.apply {
                            adapter = subwayAdapter
                            layoutManager = LinearLayoutManager(this@Subway_FravoriteDeepInfo_Activity)
                            subwayAdapter.submitList(it)
                        }
                    })

                })
        }

    }



    private fun setRecyclearView() {
        val subwayname = intent.getStringExtra("subwayname").toString()
        binding.SubwayStationName.text = subwayname
        Retrofit_Manager.retrofitManager.getsubwayCall(subwayname, null,null,null,
            mymodel = { subwaymodel ->
            subwayAdapter = SubwayAdapter()

            subwayViewModel.setSubwayItem(subwaymodel)
            subwayViewModel.subwayItem.observe(this , Observer {
                binding.SubwayFVDeepInFoRecyclerView.apply {
                    adapter = subwayAdapter
                    layoutManager = LinearLayoutManager(this@Subway_FravoriteDeepInfo_Activity)
                    subwayAdapter.submitList(it)
                }
            })

        })

    }

}