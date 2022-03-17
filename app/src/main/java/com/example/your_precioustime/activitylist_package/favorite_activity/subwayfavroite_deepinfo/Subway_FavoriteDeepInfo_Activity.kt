package com.example.your_precioustime.activitylist_package.favorite_activity.subwayfavroite_deepinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class Subway_FravoriteDeepInfo_Activity : AppCompatActivity() , CoroutineScope {

    private var SubwayFavroiteDeepInfoBinding: ActivitySubwayFavoriteDeepInfoBinding? = null
    private val binding get() = SubwayFavroiteDeepInfoBinding!!


    lateinit var subwayAdapter: SubwayAdapter
    private lateinit var subwayViewModel: SubwayViewModel

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SubwayFavroiteDeepInfoBinding =
            ActivitySubwayFavoriteDeepInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()

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


        launch(coroutineContext) {
            try {
                withContext(Dispatchers.Main){
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
            }catch (e:Exception){
                e.printStackTrace()
                Toast.makeText(this@Subway_FravoriteDeepInfo_Activity,"Error",Toast.LENGTH_SHORT).show()
                finish()
            }
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