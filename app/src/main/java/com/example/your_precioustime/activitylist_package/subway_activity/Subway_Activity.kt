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

    private var retrofitInterface =
        Retrofit_Client.getJsonClienet(SEOUL_SUBWAY_MAIN_URL).create(Retrofit_InterFace::class.java)

    lateinit var subwayAdapter: SubwayAdapter
    lateinit var subwayDataBase: SubwayDataBase
    lateinit var subwayNameListEntity: List<SubwayNameEntity>


    private lateinit var subwayRoomViewModel: Subway_FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subwayBinding = ActivitySubwayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subwayDataBase = SubwayDataBase.getinstance(this)!!
        subwayViewModel = ViewModelProvider(this).get(SubwayViewModel::class.java)
        subwayRoomViewModel = ViewModelProvider(this , Subway_FavoriteViewModel.Factory(application)).get(Subway_FavoriteViewModel::class.java)


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
            getsubwayCall(searchtext)
            binding.subwaySwipe.isRefreshing = false
        }

        binding.clickhere.setOnClickListener {
            subwayFavoriteChecking()
            val searchtext = binding.SearchEditText.text.toString()
            binding.subtitleTextView.text = searchtext
            binding.subwayfavroiteAddImageView.visibility = View.VISIBLE
            binding.subtitleTextView.visibility = View.VISIBLE
            binding.secondunderline.visibility = View.VISIBLE
            getsubwayCall(searchtext)

        }

//        binding.subwayfavroiteAddImageView.setOnClickListener {
//            val subwayname = binding.subtitleTextView.text.toString()
//
//            val mylist = SubwayNameEntity(
//                id = null,
//                subwayname
//            )
//
//            subwayinsert(mylist)
//        }


    }



    //RecyclerViewSet
    private fun getsubwayCall(statNm: String) {
        subwayAdapter = SubwayAdapter()

        val call = retrofitInterface.SUBWAYGET(
            statnNm = statNm
        )

        call.enqueue(object : retrofit2.Callback<SubwayModel> {
            override fun onResponse(call: Call<SubwayModel>, response: Response<SubwayModel>) {


                Log.d(TAG, "onResponse: ${response.body()}")


                val body = response.body()
                val subwaymodel = mutableListOf<SubwayItem>()

                body?.let {
                    val hello = body.realtimeArrivalList

                    if (hello != null) {
                        for (i in hello.indices) {
                            val firstsubwayId = hello.get(i).subwayId!!
                            val trainLineNm = hello.get(i).trainLineNm
                            val bstatnNm = hello.get(i).bstatnNm
                            val arvlMsg2 = hello.get(i).arvlMsg2

                            subwaymodel.add(
                                SubwayItem(firstsubwayId, trainLineNm, bstatnNm, arvlMsg2)
                            )

                        }

                    } else {
                        Myobject.myobject.retrystation(binding.subwayActivity)
                        binding.subtitleTextView.visibility = View.INVISIBLE
                        binding.subwayfavroiteAddImageView.visibility = View.INVISIBLE
                    }


                    Log.d(TAG, "onResponse: $subwaymodel")

                    for (i in subwaymodel.indices) {
                        when (subwaymodel[i].subwayId) {
                            "1001" -> {
                                subwaymodel[i].subwayId = "1"
                            }
                            "1002" -> {
                                subwaymodel[i].subwayId = "2"
                            }

                            "1003" -> {
                                subwaymodel[i].subwayId = "3"
                            }

                            "1004" -> {
                                subwaymodel[i].subwayId = "4"
                            }

                            "1005" -> {
                                subwaymodel[i].subwayId = "5"
                            }
                            "1006" -> {
                                subwaymodel[i].subwayId = "6"
                            }

                            "1007" -> {
                                subwaymodel[i].subwayId = "7"
                            }
                            "1008" -> {
                                subwaymodel[i].subwayId = "8"
                            }

                            "1009" -> {
                                subwaymodel[i].subwayId = "9"
                            }

                            "1063" -> {
                                subwaymodel[i].subwayId = "경"
                            }

                            "1065" -> {
                                subwaymodel[i].subwayId = "공"
                            }

                            "1067" -> {
                                subwaymodel[i].subwayId = "경춘"
                            }


                            "1075" -> {
                                subwaymodel[i].subwayId = "수"
                            }


                            "1077" -> {
                                subwaymodel[i].subwayId = "신"
                            }

                            "1091" -> {
                                subwaymodel[i].subwayId = "자"
                            }

                            "1092" -> {
                                subwaymodel[i].subwayId = "우"
                            }

                        }
                    }

                    //LiveData, ViewModel
                    subwayViewModel.setSubwayItem(subwaymodel)
                    subwayViewModel.subwayItem.observe(this@Subway_Activity, Observer { it->
                        binding.subwayRecyclerView.apply {
                            adapter = subwayAdapter
                            layoutManager = LinearLayoutManager(context)
                            subwayAdapter.submitList(it)
                        }

                    })

                }


            }

            override fun onFailure(call: Call<SubwayModel>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })

    }



    private fun subwayRoomFavroiteInsert(){
        binding.subwayfavroiteAddImageView.setOnClickListener {
            val subwayname = binding.subtitleTextView.text.toString()
            subwayRoomViewModel.subwayInsert(subwayname)

            Myobject.myobject.FavroiteSnackBar(binding.subwayActivity)
        }
    }

    private fun subwayFavoriteChecking(){

        subwayRoomViewModel.subwaygetAll().observe(this, Observer { SubwayFavoriteEntity->
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


    private fun subwayinsert(subwayNameEntity: SubwayNameEntity) {
        val insertTask = (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                subwayNameListEntity = subwayDataBase.subwayNameDAO().subwayGetAll()

                Log.d(TAG, "지하철이름저장 로그: $subwayNameListEntity")

                val subwaynameList = mutableListOf<String>()
                for (i in subwayNameListEntity.indices) {
                    val subwayname = subwayNameListEntity.get(i).subwayName
                    subwaynameList.add(subwayname)
                }

                if (binding.subtitleTextView.text !in subwaynameList) {
                    subwayDataBase.subwayNameDAO().subwayInsert(subwayNameEntity)
                }

            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)

                val stationnameList = mutableListOf<String>()

                for (i in subwayNameListEntity.indices) {
                    val stationname = subwayNameListEntity.get(i).subwayName
                    stationnameList.add(stationname)
                }

                if (binding.subtitleTextView.text in stationnameList) {
                    Myobject.myobject.alreadyFavroiteSnackBar(binding.subwayActivity)
                } else {
                    Myobject.myobject.FavroiteSnackBar(binding.subwayActivity)
                    binding.subwayfavroiteAddImageView.setImageResource(R.drawable.shinigstar)
                }


            }

        }).execute()
    }

    private fun testgetAll() {
        val getAllTask = (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                subwayNameListEntity = subwayDataBase.subwayNameDAO().subwayGetAll()
                Log.d(TAG, "지하철이름저장 로그: $subwayNameListEntity")
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                val stationnameList = mutableListOf<String>()

                for (i in subwayNameListEntity.indices) {
                    val stationname = subwayNameListEntity.get(i).subwayName
                    stationnameList.add(stationname)
                }

                if (binding.subtitleTextView.text in stationnameList) {
                    binding.subwayfavroiteAddImageView.setImageResource(R.drawable.shinigstar)
                } else {
                    binding.subwayfavroiteAddImageView.setImageResource(R.drawable.star)
                }

            }

        }).execute()
    }
}