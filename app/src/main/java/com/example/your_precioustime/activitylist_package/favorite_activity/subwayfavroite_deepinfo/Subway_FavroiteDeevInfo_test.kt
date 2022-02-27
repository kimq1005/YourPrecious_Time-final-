package com.example.your_precioustime.activitylist_package.favorite_activity.subwayfavroite_deepinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.your_precioustime.databinding.ActivitySubwayFavroiteDeevInfoBinding

class Subway_FavroiteDeevInfo_test : AppCompatActivity() {

    var subwayFavroiteDeevInfoBinding: ActivitySubwayFavroiteDeevInfoBinding? = null
    val binding get() = subwayFavroiteDeevInfoBinding!!

//    private var retrofitInterface = Retrofit_Client.getJsonClienet(Url.SEOUL_SUBWAY_MAIN_URL)
//        .create(Retrofit_InterFace::class.java)
//
//    lateinit var subwayAdapter: SubwayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subwayFavroiteDeevInfoBinding = ActivitySubwayFavroiteDeevInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        setRecyclearView()

//        subwaySetRecyclerView("매탄권선")

//        binding.backbtn.setOnClickListener {
//            onBackPressed()
//            finish()
//        }

//        Myobject.myobject.ToggleSet(
//            this,
//            binding.floatingBtn,
//            binding.FavroiteFloatBtn,
//            binding.SubwayFloatBtn,
//            binding.BusfloatBtn
//        )


    }

//    private fun setRecyclearView() {
//        val subwayname = intent.getStringExtra("subwayname").toString()
//        binding.SubwayStationName.text = subwayname
//        retrofitManager.getsubwayCall(subwayname, mymodel = { subwaymodel ->
//            subwayAdapter = SubwayAdapter()
//            binding.SubwayFVDeepInFoRecyclerView.apply {
//                adapter = subwayAdapter
//                layoutManager = LinearLayoutManager(this@Subway_FavroiteDeevInfo_test)
//                subwayAdapter.submitList(subwaymodel)
//            }
//        })
//
//    }


//    private fun subwaySetRecyclerView(subwayNm:String){
//        val call = retrofitInterface.SUBWAYGET(statnNm=subwayNm)
//
//        call.enqueue(object :retrofit2.Callback<SubwayModel>{
//            override fun onResponse(call: Call<SubwayModel>, response: Response<SubwayModel>) {
//                Log.d(TAG, "onResponse: ${response.body()} ")
//            }
//
//            override fun onFailure(call: Call<SubwayModel>, t: Throwable) {
//                Log.d(TAG, "onFailure: $t")
//            }
//        })
//
//    }


}